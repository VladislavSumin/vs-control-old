package ru.vs.control.service_cams_netsurv

import org.kodein.di.DI
import ru.vs.core.di.Modules

fun Modules.featureServiceCamsNetsurv() = DI.Module("feature-service-cams-netsurv") {
    importOnce(Modules.featureServiceCamsNetsurvShared())
}
