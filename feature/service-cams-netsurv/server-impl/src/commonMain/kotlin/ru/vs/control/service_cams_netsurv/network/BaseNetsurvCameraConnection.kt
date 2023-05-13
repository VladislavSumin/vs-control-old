package ru.vs.control.service_cams_netsurv.network

import io.github.oshai.KotlinLogging
import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.core.readBytes
import io.ktor.utils.io.core.use
import io.ktor.utils.io.readIntLittleEndian
import io.ktor.utils.io.readShortLittleEndian
import io.ktor.utils.io.writeFully
import io.ktor.utils.io.writeIntLittleEndian
import io.ktor.utils.io.writeShortLittleEndian
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

private const val AUTH_RESPONSE_TIMEOUT = 20_000L
private const val PING_RESPONSE_TIMEOUT = 20_000L
private const val PING_SEND_INTERVAL = 10_000L
private const val FIRST_PING_SEND_INTERVAL = 1_000L
private const val PROCESS_RECEIVED_MESSAGE_TIMEOUT = 5_000L

@Suppress("UnnecessaryAbstractClass")
internal abstract class BaseNetsurvCameraConnection(
    private val selectorManager: SelectorManager,
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

                val writeMessagesChannel = Channel<Msg>(capacity = Channel.RENDEZVOUS)
                val writeMessagesTask = launch {
                    for (msg in writeMessagesChannel) {
                        write(msg)
                    }
                }

                val receiveMessagesChannel = Channel<Msg>(capacity = Channel.RENDEZVOUS)
                val readMessagesTask = launch {
                    while (true) {
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
                                    withTimeout(PING_RESPONSE_TIMEOUT) { read() }
                                } catch (e: TimeoutCancellationException) {
                                    // TimeoutCancellationException doesn't crash parent scope
                                    throw TimeoutException("Timeout while processing message", e)
                                }
                            }
                        }
                    }
                }

                val pingTask = launch {
                    // interesting fact, netsurv cams ignore first ping request
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

                pingTask.cancel()
                writeMessagesTask.cancel()
                readMessagesTask.cancel()
            }
        }
    }

    /**
     * Connect and hold connection opened while [block] is executing
     * Additionally wrapping [runWithRawConnection] and cast raw byte channels communication
     * to [Msg] based communication
     */
    private suspend inline fun runWithConnection(
        block: (
            read: suspend () -> Msg,
            write: suspend (msg: Msg) -> Unit
        ) -> Unit
    ) {
        runWithRawConnection { readChannel, writeChannel ->
            block(
                { Msg.decodeFromChannel(readChannel) },
                { msg -> msg.encodeToChannel(writeChannel); writeChannel.flush() }
            )
        }
    }

    /**
     * Connect and hold connection opened while [block] is executing
     * @param block - lambda with [ByteReadChannel] and [ByteWriteChannel] to communicate over socket
     */
    private suspend inline fun runWithRawConnection(
        block: (
            readChannel: ByteReadChannel,
            writeChannel: ByteWriteChannel
        ) -> Unit
    ) {
        logger.trace { "Connecting to $hostname:$port" }
        try {
            aSocket(selectorManager)
                .tcp()
                .connect(hostname, port)
                .use { socket ->
                    val readChannel: ByteReadChannel = socket.openReadChannel()
                    val writeChannel: ByteWriteChannel = socket.openWriteChannel()

                    logger.trace { "Tcp connection with $hostname:$port established" }

                    block(readChannel, writeChannel)
                }
        } finally {
            logger.trace { "Tcp connection with $hostname:$port closed" }
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

/**
 * Read [Msg] from [readChannel]
 */
private suspend fun Msg.Companion.decodeFromChannel(readChannel: ByteReadChannel): Msg {
    readChannel.apply {
        val headFlag = readByte()
        val version = readByte()
        val reserved01 = readByte()
        val reserved02 = readByte()
        val sessionId = readIntLittleEndian()
        val sequenceNumber = readIntLittleEndian()
        val totalPacket = readByte()
        val currentPacket = readByte()
        val messageId = CommandCode.fromCode(readShortLittleEndian().toInt())
        val dataLength = readIntLittleEndian()
        val data = readPacket(dataLength).readBytes()

        return Msg(
            headFlag,
            version,
            reserved01,
            reserved02,
            sessionId,
            sequenceNumber,
            totalPacket,
            currentPacket,
            messageId,
            dataLength,
            data
        )
    }
}

/**
 * Write [Msg] to [writeChannel]
 */
private suspend fun Msg.encodeToChannel(writeChannel: ByteWriteChannel) {
    writeChannel.apply {
        writeByte(headFlag)
        writeByte(version)
        writeByte(reserved01)
        writeByte(reserved02)
        writeIntLittleEndian(sessionId)
        writeIntLittleEndian(sequenceNumber)
        writeByte(totalPacket)
        writeByte(currentPacket)
        writeShortLittleEndian(messageId.code.toShort())
        writeIntLittleEndian(dataLength)
        writeFully(data)
    }
}
