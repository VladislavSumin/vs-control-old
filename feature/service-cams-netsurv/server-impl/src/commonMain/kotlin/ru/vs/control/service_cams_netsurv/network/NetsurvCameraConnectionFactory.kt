package ru.vs.control.service_cams_netsurv.network

import io.ktor.network.selector.SelectorManager
import kotlinx.coroutines.CoroutineScope

internal interface NetsurvCameraConnectionFactory {
    fun createTelemetry(hostname: String, port: Int): TelemetryNetsurvCameraConnection
    fun createVideo(hostname: String, port: Int): VideoStreamNetsurvCameraConnection
}

internal class NetsurvCameraConnectionFactoryImpl(
    private val selectorManager: SelectorManager,
    private val scope: CoroutineScope,
) : NetsurvCameraConnectionFactory {
    override fun createTelemetry(hostname: String, port: Int): TelemetryNetsurvCameraConnection {
        return TelemetryNetsurvCameraConnection(selectorManager, scope, hostname, port)
    }

    override fun createVideo(hostname: String, port: Int): VideoStreamNetsurvCameraConnection {
        return VideoStreamNetsurvCameraConnection(selectorManager, hostname, port)
    }
}
