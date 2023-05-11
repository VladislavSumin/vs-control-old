package ru.vs.control.service_cams_netsurv.protocol

/**
 * Decode && encode message support function
 */
internal object ChannelMsgDecoder {
    /**
     * Read next msg from channel
     */
//    fun decode(input: ReadableByteChannel): Msg {
//        with(Msg()) {
//            var buffer = ByteBuffer.allocate(20)
//            buffer.order(ByteOrder.LITTLE_ENDIAN)
//            input.read(buffer)
//            buffer.flip()
//
//            headFlag = buffer.get()
//            version = buffer.get()
//            reserved01 = buffer.get()
//            reserved02 = buffer.get()
//            sessionId = buffer.int
//            sequenceNumber = buffer.int
//            totalPacket = buffer.get()
//            currentPacket = buffer.get()
//            val tmp = buffer.short.toInt()
//            messageId = CommandCode.fromCode(tmp)
//
//            dataLength = buffer.int
//            buffer = ByteBuffer.allocate(dataLength)
//            input.read(buffer)
//            buffer.flip()
//            data = buffer.array()
//
//            return this
//        }
//    }

    /**
     * Write msg to channel
     */
//    fun encode(out: WritableByteChannel, msg: Msg) {
//        with(msg) {
//            val buffer = ByteBuffer.allocate(20 + msg.dataLength)
//            buffer.order(ByteOrder.LITTLE_ENDIAN)
//
//            with(buffer) {
//                put(headFlag)
//                put(version)
//                put(reserved01)
//                put(reserved02)
//                putInt(sessionId)
//                putInt(sequenceNumber)
//                put(totalPacket)
//                put(currentPacket)
//                putShort(messageId.code.toShort())
//                putInt(dataLength)
//                put(data)
//
//                flip()
//                out.write(this)
//            }
//        }
//    }
}
