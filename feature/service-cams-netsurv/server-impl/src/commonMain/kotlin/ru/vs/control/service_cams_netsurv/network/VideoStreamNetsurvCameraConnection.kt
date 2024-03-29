package ru.vs.control.service_cams_netsurv.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vs.control.service_cams_netsurv.protocol.CommandCode
import ru.vs.control.service_cams_netsurv.protocol.CommandRepository
import ru.vs.core.network.service.NetworkService

internal class VideoStreamNetsurvCameraConnection(
    networkService: NetworkService,
    hostname: String,
    port: Int
) : BaseNetsurvCameraConnection(networkService, hostname, port) {
    /**
     * Connects to camera, authorize, request live video stream and emits it to output flow
     * On network or other error don't try to reconnect and throw error to output flow
     */
    fun observeVideoStream(): Flow<ByteArray> {
        return flow {
            runWithAuthenticatedConnection { sessionId, read, write ->
                // Claim monitor
                write(CommandRepository.monitorClaim(sessionId))
                check(read().messageId == CommandCode.MONITOR_CLAIM_RSP)

                // Monitor start
                write(CommandRepository.monitorStart(sessionId))

                while (true) {
                    val msg = read()
                    check(msg.messageId == CommandCode.MONITOR_DATA)
                    emit(msg.data)
                }
            }
        }
    }
}
