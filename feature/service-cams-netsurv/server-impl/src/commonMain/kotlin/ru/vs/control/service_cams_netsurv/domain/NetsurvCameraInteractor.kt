package ru.vs.control.service_cams_netsurv.domain

import ru.vs.control.service_cams_netsurv.network.NetsurvCameraConnectionFactory
import ru.vs.control.service_cams_netsurv.network.TelemetryNetsurvCameraConnection
import ru.vs.control.service_cams_netsurv.network.VideoStreamNetsurvCameraConnection

internal interface NetsurvCameraInteractor {
    val camera: NetsurvCamera
    val telemetryConnection: TelemetryNetsurvCameraConnection
    val videoStreamConnection: VideoStreamNetsurvCameraConnection
}

internal class NetsurvCameraInteractorImpl(
    override val camera: NetsurvCamera,
    connectionFactory: NetsurvCameraConnectionFactory,
) : NetsurvCameraInteractor {

    // We have to separate connection here, because have functionality to receive video stream only if it needed
    // but netsurv cams not running video stream again after cancel it (need connection restart).
    // To avoid telemetry lost on stop video stream using two separate connection
    override val telemetryConnection = connectionFactory.createTelemetry(camera.hostname, camera.port)
    override val videoStreamConnection = connectionFactory.createVideo(camera.hostname, camera.port)
}
