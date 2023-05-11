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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vs.control.service_cams_netsurv.protocol.CommandCode
import ru.vs.control.service_cams_netsurv.protocol.CommandRepository
import ru.vs.control.service_cams_netsurv.protocol.Msg

internal class NetsurvCameraConnection(
    private val selectorManager: SelectorManager,
    private val hostname: String,
    private val port: Int,
) {

    private val logger = KotlinLogging.logger("NetsurvCameraConnection")

    fun observeConnectionStatus(): Flow<Boolean> {
        return flow {
            emit(false)
            try {
                runWithAuthenticatedConnection { _, _, _ ->
                    emit(true)
                    delay(Long.MAX_VALUE)
                }
            } finally {
                emit(false)
            }
        }
    }

    /**
     * Connect authenticate and hold connection opened while [block] is executing
     * Additionally wrapping [runWithConnection] and add authentication before execute [block]
     */
    private suspend inline fun runWithAuthenticatedConnection(
        block: (
            sessionId: Int,
            read: suspend () -> Msg,
            write: suspend (msg: Msg) -> Unit
        ) -> Unit
    ) {
        runWithConnection { read, write ->

            // Authenticate
            logger.trace { "Authenticating in $hostname:$port" }
            write(CommandRepository.auth())
            val sessionId = read().sessionId
            logger.trace { "Authenticated in $hostname:$port, sessionId=$sessionId" }

            block(sessionId, read, write)
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
