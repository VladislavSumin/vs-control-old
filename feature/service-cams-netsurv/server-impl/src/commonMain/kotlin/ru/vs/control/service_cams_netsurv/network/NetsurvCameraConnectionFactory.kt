package ru.vs.control.service_cams_netsurv.network

import kotlinx.coroutines.CoroutineScope
import ru.vs.core.network.service.NetworkService

internal interface NetsurvCameraConnectionFactory {
    fun createTelemetry(hostname: String, port: Int): TelemetryNetsurvCameraConnection
    fun createVideo(hostname: String, port: Int): VideoStreamNetsurvCameraConnection
}

internal class NetsurvCameraConnectionFactoryImpl(
    private val networkService: NetworkService,
    private val scope: CoroutineScope,
) : NetsurvCameraConnectionFactory {
    override fun createTelemetry(hostname: String, port: Int): TelemetryNetsurvCameraConnection {
        return TelemetryNetsurvCameraConnection(networkService, scope, hostname, port)
    }

    override fun createVideo(hostname: String, port: Int): VideoStreamNetsurvCameraConnection {
        return VideoStreamNetsurvCameraConnection(networkService, hostname, port)
    }
}
