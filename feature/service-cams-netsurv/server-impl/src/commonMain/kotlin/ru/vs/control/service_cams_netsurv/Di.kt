package ru.vs.control.service_cams_netsurv

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.service_cams_netsurv.domain.NetsurvCameraInteractorFactory
import ru.vs.control.service_cams_netsurv.domain.NetsurvCameraInteractorFactoryImpl
import ru.vs.control.service_cams_netsurv.domain.NetsurvCamsInteractor
import ru.vs.control.service_cams_netsurv.domain.NetsurvCamsInteractorImpl
import ru.vs.control.service_cams_netsurv.domain.NetsurvCamsService
import ru.vs.control.service_cams_netsurv.domain.NetsurvCamsServiceImpl
import ru.vs.control.service_cams_netsurv.network.NetsurvCameraConnectionFactory
import ru.vs.control.service_cams_netsurv.network.NetsurvCameraConnectionFactoryImpl
import ru.vs.control.service_cams_netsurv.repository.NetsurvCamsRepository
import ru.vs.control.service_cams_netsurv.repository.NetsurvCamsRepositoryImpl
import ru.vs.control.service_cams_netsurv.rsub.NetsurvCamsRsub
import ru.vs.control.service_cams_netsurv.rsub.NetsurvCamsRsubImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureServiceCamsNetsurv() = DI.Module("feature-service-cams-netsurv") {
    importOnce(Modules.featureServiceCamsNetsurvShared())

    // rSub
    bindSingleton<NetsurvCamsRsub> { NetsurvCamsRsubImpl(i()) }

    // Network
    bindSingleton<NetsurvCameraConnectionFactory> { NetsurvCameraConnectionFactoryImpl(i(), i()) }

    // Repository
    bindSingleton<NetsurvCamsRepository> { NetsurvCamsRepositoryImpl() }

    // Domain
    bindSingleton<NetsurvCameraInteractorFactory> { NetsurvCameraInteractorFactoryImpl(i()) }
    bindSingleton<NetsurvCamsInteractor> { NetsurvCamsInteractorImpl(i(), i()) }
    bindSingleton<NetsurvCamsService> { NetsurvCamsServiceImpl(i(), i()) }
}
