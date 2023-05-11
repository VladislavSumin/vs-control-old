package ru.vs.control.service_cams_netsurv.network

import io.ktor.network.selector.SelectorManager

internal interface NetsurvCameraConnectionFactory {
    fun create(hostname: String, port: Int): NetsurvCameraConnection
}

internal class NetsurvCameraConnectionFactoryImpl(
    private val selectorManager: SelectorManager,
) : NetsurvCameraConnectionFactory {
    override fun create(hostname: String, port: Int): NetsurvCameraConnection {
        return NetsurvCameraConnection(selectorManager, hostname, port)
    }
}
