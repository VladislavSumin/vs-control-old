package ru.vs.control.service_cams_netsurv.network

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import ru.vs.control.service_cams_netsurv.protocol.CommandCode
import ru.vs.control.service_cams_netsurv.protocol.CommandRepository
import ru.vs.control.service_cams_netsurv.protocol.Msg
import ru.vs.control.service_cams_netsurv.protocol.decodeFromChannel
import ru.vs.control.service_cams_netsurv.protocol.encodeToChannel
import ru.vs.core.network.service.NetworkService
import ru.vs.core.network.service.openTcpSocket

private const val AUTH_RESPONSE_TIMEOUT = 20_000L
private const val PING_RESPONSE_TIMEOUT = 20_000L
private const val PING_SEND_INTERVAL = 10_000L
private const val FIRST_PING_SEND_INTERVAL = 2_000L
private const val PROCESS_RECEIVED_MESSAGE_TIMEOUT = 5_000L

/**
 * Base connection with netsurv camera.
 * This connection parse [Msg] and work with low level protocol part + handle two additional function:
 * 1. Authentication
 * 2. Send ping packages, handle responses and close connection by timeout (ping is requiring as protocol part)
 *
 * @param networkService network service instance
 * @param hostname camera hostname
 * @param port camera port
 * @param reconnectInterval time between connection retries
 */
@Suppress("UnnecessaryAbstractClass")
internal abstract class BaseNetsurvCameraConnection(
    private val networkService: NetworkService,
    protected val hostname: String,
    protected val port: Int,
    private val reconnectInterval: Long = 5_000L,
) {

    protected val logger = KotlinLogging.logger(this::class.simpleName ?: "<Unnamed>BaseNetsurvCameraConnection")

    /**
     * Apply reconnection policy to [runWithAuthenticatedConnection] function
     * Holds connection && try to reconnect on exceptions while run [block] or on internal connection exception.
     * If [block] successfully finished close connection and complete flow
     */
    protected fun <T> runWithAutoReconnect(
        block: suspend ProducerScope<T>.(
            sessionId: Int,
            read: suspend () -> Msg,
            write: suspend (msg: Msg) -> Unit
        ) -> Unit
    ): Flow<ConnectionState<T>> = channelFlow {
        // Send initial state
        send(ConnectionState.Connecting)

        var isRunning = true
        while (isRunning) {
            try {
                runWithAuthenticatedConnection { sessionId, read, write ->

                    channelFlow { block(sessionId, read, write) }
                        .map { ConnectionState.Connected(it) }
                        .collect { send(it) }

                    // if we go here without exception then close connection
                    isRunning = false
                }
            } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
                send(ConnectionState.Reconnecting(e))
                delay(reconnectInterval)
            }
        }
    }

    /**
     * Connect authenticate and hold connection opened while [block] is executing
     * Additionally wrapping [runWithConnection] add authentication before execute [block] and add ping required by
     * netsurv cams protocol
     */
    protected suspend inline fun runWithAuthenticatedConnection(
        crossinline block: suspend (
            sessionId: Int,
            read: suspend () -> Msg,
            write: suspend (msg: Msg) -> Unit
        ) -> Unit
    ) {
        runWithConnection { read, write ->
            coroutineScope {
                // Authenticate
                logger.trace { "Authenticating in $hostname:$port" }
                val sessionId = withTimeout(AUTH_RESPONSE_TIMEOUT) {
                    write(CommandRepository.auth())
                    val msg = read()
                    check(msg.messageId == CommandCode.LOGIN_RSP)
                    // TODO Check message search return code and verify is login valid
                    msg.sessionId
                }
                logger.trace { "Authenticated in $hostname:$port, sessionId=$sessionId" }

                // Create write channel && writeMessagesTask for purpose of synchronization when we send msg
                // from different places
                val writeMessagesChannel = Channel<Msg>(capacity = Channel.RENDEZVOUS)
                val writeMessagesTask = launch {
                    for (msg in writeMessagesChannel) {
                        write(msg)
                    }
                }

                // Create receive channel, to this channel we send received messages (if its type is not a ping)
                val receiveMessagesChannel = Channel<Msg>(capacity = Channel.RENDEZVOUS)
                val readMessagesTask = launch {
                    while (true) {
                        // Read message with timeout
                        val msg = try {
                            withTimeout(PING_RESPONSE_TIMEOUT) { read() }
                        } catch (e: TimeoutCancellationException) {
                            // TimeoutCancellationException doesn't crash parent scope
                            throw TimeoutException("Timeout while reading message", e)
                        }
                        when (msg.messageId) {
                            CommandCode.KEEPALIVE_RSP -> {
                                // logic here is simple, we received messages with timeout
                                // if no message will be received after given timeout we reset the connection
                                // ping response resetting counter

                                // additionally we filter this

                                logger.trace { "Receiving ping response from $hostname:$port" }
                            }

                            else -> {
                                try {
                                    // We use rendezvous channel, and for guarantee correct message receiving
                                    // we add timeout here, to drop connection if we have unprocessed messages
                                    withTimeout(PROCESS_RECEIVED_MESSAGE_TIMEOUT) { receiveMessagesChannel.send(msg) }
                                } catch (e: TimeoutCancellationException) {
                                    // TimeoutCancellationException doesn't crash parent scope
                                    throw TimeoutException("Timeout while processing message", e)
                                }
                            }
                        }
                    }
                }

                // netsurv protocols requiring periodical ping requests from client side, if we don't send ping
                // camera close connection after some time
                val pingTask = launch {
                    delay(FIRST_PING_SEND_INTERVAL)
                    while (true) {
                        logger.trace { "Sending ping request to $hostname:$port" }
                        writeMessagesChannel.send(CommandRepository.keepAlive(sessionId))
                        delay(PING_SEND_INTERVAL)
                    }
                }

                block(
                    sessionId,
                    { receiveMessagesChannel.receive() },
                    { msg -> writeMessagesChannel.send(msg) }
                )

                // If block finished gracefully we need to close all launched task, if we don't do this we stuck
                // when trying leave from contention coroutine scope. When block throws exception all this task
                // cancelling automatically.
                pingTask.cancel()
                writeMessagesTask.cancel()
                readMessagesTask.cancel()
            }
        }
    }

    /**
     * Connect and hold connection opened while [block] is executing
     * Mapping raw byte channel into [Msg] input/output
     */
    private suspend inline fun runWithConnection(
        block: (
            read: suspend () -> Msg,
            write: suspend (msg: Msg) -> Unit
        ) -> Unit
    ) {
        networkService.openTcpSocket(hostname, port) { readChannel, writeChannel ->
            block(
                { Msg.decodeFromChannel(readChannel) },
                { msg ->
                    msg.encodeToChannel(writeChannel)
                    writeChannel.flush()
                }
            )
        }
    }

    /**
     * Connection states statuses for [runWithAutoReconnect] function
     */
    sealed interface ConnectionState<out T> {
        /**
         * Emits at fists connection try
         */
        object Connecting : ConnectionState<Nothing>

        /**
         * Emits by outer logic inside [runWithAutoReconnect] block function
         * While outer logic doesn't emit this state status will be [Connecting] or [Reconnecting]
         *
         * @param state - any payload for outer logic
         */
        data class Connected<T>(val state: T) : ConnectionState<T>

        /**
         * Emits immediately after connection exception.
         */
        data class Reconnecting(val e: Exception) : ConnectionState<Nothing>
    }

    class TimeoutException(message: String, cause: Throwable) : RuntimeException(message, cause)
}
