package ru.vs.control.service_cams_netsurv.network

import io.ktor.network.selector.SelectorManager

internal class VideoStreamNetsurvCameraConnection(
    selectorManager: SelectorManager,
    hostname: String,
    port: Int
) : BaseNetsurvCameraConnection(selectorManager, hostname, port)
