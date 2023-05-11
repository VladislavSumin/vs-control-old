package ru.vs.control.service_cams_netsurv.network

import io.ktor.network.selector.SelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.ByteWriteChannel
import io.ktor.utils.io.core.use
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class NetsurvCameraConnection(
    private val selectorManager: SelectorManager,
    private val hostname: String,
    private val port: Int,
) {

    fun observeConnectionStatus(): Flow<Boolean> {
        return flow {
            emit(false)
            try {
                runWithConnection { _, _ ->
                    emit(true)
                    delay(Long.MAX_VALUE)
                }
            } finally {
                emit(false)
            }
        }
    }

    private suspend fun runWithConnection(
        block: suspend (
            readChannel: ByteReadChannel,
            writeChannel: ByteWriteChannel
        ) -> Unit
    ) {
        aSocket(selectorManager)
            .tcp()
            .connect(hostname, port)
            .use { socket ->
                val readChannel: ByteReadChannel = socket.openReadChannel()
                val writeChannel: ByteWriteChannel = socket.openWriteChannel()

                block(readChannel, writeChannel)
            }
    }
}
