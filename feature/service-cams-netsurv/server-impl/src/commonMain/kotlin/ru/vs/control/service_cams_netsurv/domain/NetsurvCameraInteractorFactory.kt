package ru.vs.control.service_cams_netsurv.domain

internal interface NetsurvCameraInteractorFactory {
    fun create(camera: NetsurvCamera): NetsurvCameraInteractor
}

internal class NetsurvCameraInteractorFactoryImpl : NetsurvCameraInteractorFactory {
    override fun create(camera: NetsurvCamera): NetsurvCameraInteractor {
        return NetsurvCameraInteractorImpl(camera)
    }
}
