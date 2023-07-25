package ru.vs.control.service_cams_netsurv.protocol

import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.core.readBytes
import io.ktor.utils.io.readIntLittleEndian
import io.ktor.utils.io.readShortLittleEndian
import io.ktor.utils.io.writeFully
import io.ktor.utils.io.writeIntLittleEndian
import io.ktor.utils.io.writeShortLittleEndian

/**
 * Read [Msg] from [readChannel]
 */
internal suspend fun Msg.Companion.decodeFromChannel(readChannel: ByteReadChannel): Msg {
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
internal suspend fun Msg.encodeToChannel(writeChannel: ByteWriteChannel) {
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
