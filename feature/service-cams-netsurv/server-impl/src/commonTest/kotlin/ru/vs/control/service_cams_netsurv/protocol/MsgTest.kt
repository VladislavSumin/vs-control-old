package ru.vs.control.service_cams_netsurv.protocol

import io.ktor.utils.io.ByteChannel
import io.ktor.utils.io.close
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class MsgTest {
    @Test
    fun msgConstruct() {
        Msg.createRandom()
    }

    @Test
    fun msgSerializeDeserialize(): Unit = runBlocking {
        val channel = ByteChannel(autoFlush = true)
        val msg1 = Msg.createRandom()
        launch {
            msg1.encodeToChannel(channel)
        }
        val msg2 = Msg.decodeFromChannel(channel)
        assertEquals(msg1, msg2)
    }
}
