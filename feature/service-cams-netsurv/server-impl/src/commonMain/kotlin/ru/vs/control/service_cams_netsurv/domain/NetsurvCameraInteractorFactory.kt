package ru.vs.control.service_cams_netsurv.domain

import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.service_cams_netsurv.network.NetsurvCameraConnectionFactory

internal interface NetsurvCameraInteractorFactory {
    fun create(camera: NetsurvCamera): NetsurvCameraInteractor
}

internal class NetsurvCameraInteractorFactoryImpl(
    private val netsurvCameraConnectionFactory: NetsurvCameraConnectionFactory,
    private val entitiesInteractor: EntitiesInteractor,
) : NetsurvCameraInteractorFactory {
    override fun create(camera: NetsurvCamera): NetsurvCameraInteractor {
        return NetsurvCameraInteractorImpl(camera, entitiesInteractor, netsurvCameraConnectionFactory)
    }
}
