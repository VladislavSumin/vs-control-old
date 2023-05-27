package ru.vs.control.service_cams_netsurv

import org.kodein.di.DI
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactory
import ru.vs.control.service_cams_netsurv.ui.live_video_stream_entity_state.NetsurvLiveVideoStreamEntityStateComponentFactory
import ru.vs.core.di.Modules

fun Modules.featureServiceCamsNetsurv() = DI.Module("feature-service-cams-netsurv") {
    importOnce(Modules.featureServiceCamsNetsurvShared())

    inBindSet<EntityStateComponentFactory<*>> {
        add { singleton { NetsurvLiveVideoStreamEntityStateComponentFactory() } }
    }
}
