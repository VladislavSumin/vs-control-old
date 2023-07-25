package ru.vs.control.service_cams_netsurv.protocol

import kotlin.random.Random
import kotlin.random.nextInt

internal fun Msg.Companion.createRandom(): Msg {
    val dataLen = Random.nextInt(0..32)
    val data = ByteArray(dataLen) { Random.nextInt().toByte() }
    return Msg(
        headFlag = Random.nextInt().toByte(),
        version = Random.nextInt().toByte(),
        reserved01 = Random.nextInt().toByte(),
        reserved02 = Random.nextInt().toByte(),
        sessionId = Random.nextInt(),
        sequenceNumber = Random.nextInt(),
        totalPacket = Random.nextInt().toByte(),
        currentPacket = Random.nextInt().toByte(),
        messageId = CommandCode.values().random(),
        dataLength = dataLen,
        data = data,
    )
}
