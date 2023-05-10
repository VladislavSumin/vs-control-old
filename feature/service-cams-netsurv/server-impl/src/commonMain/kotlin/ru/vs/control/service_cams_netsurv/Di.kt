package ru.vs.control.service_cams_netsurv

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.service_cams_netsurv.domain.NetsurvCamsService
import ru.vs.control.service_cams_netsurv.domain.NetsurvCamsServiceImpl
import ru.vs.core.di.Modules

fun Modules.featureServiceCamsNetsurv() = DI.Module("feature-service-cams-netsurv") {
    bindSingleton<NetsurvCamsService> { NetsurvCamsServiceImpl() }
}
