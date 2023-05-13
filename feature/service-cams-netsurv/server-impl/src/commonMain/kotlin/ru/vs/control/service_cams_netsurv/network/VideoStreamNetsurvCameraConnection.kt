package ru.vs.control.service_cams_netsurv.network

import io.ktor.network.selector.SelectorManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vs.control.service_cams_netsurv.protocol.CommandCode
import ru.vs.control.service_cams_netsurv.protocol.CommandRepository

internal class VideoStreamNetsurvCameraConnection(
    selectorManager: SelectorManager,
    hostname: String,
    port: Int
) : BaseNetsurvCameraConnection(selectorManager, hostname, port) {
    fun observeVideoStream(): Flow<ByteArray> {
        return flow {
            runWithAuthenticatedConnection { sessionId, read, write ->
                // Claim monitor
                write(CommandRepository.monitorClaim(sessionId))
                check(read().messageId == CommandCode.MONITOR_CLAIM_RSP)

                // Monitor start
                write(CommandRepository.monitorStart(sessionId))

                emit(read().data)
            }
        }
    }
}
