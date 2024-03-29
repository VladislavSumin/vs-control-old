package ru.vs.control.service_cams_netsurv

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactory
import ru.vs.control.service_cams_netsurv.ui.composite_entity_state.NetsurvCompositeEntityStateComponentFactory
import ru.vs.control.service_cams_netsurv.ui.composite_entity_state.NetsurvCompositeEntityStateViewModelFactory
import ru.vs.control.service_cams_netsurv.ui.live_video_stream_entity_state.NetsurvLiveVideoStreamEntityStateComponentFactory
import ru.vs.control.service_cams_netsurv.ui.live_video_stream_entity_state.NetsurvLiveVideoStreamEntityStateViewModelFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureServiceCamsNetsurv() = DI.Module("feature-service-cams-netsurv") {
    importOnce(Modules.featureServiceCamsNetsurvShared())

    inBindSet<EntityStateComponentFactory<*>> {
        add { singleton { NetsurvCompositeEntityStateComponentFactory(i()) } }
        add { singleton { NetsurvLiveVideoStreamEntityStateComponentFactory(i()) } }
    }

    bindSingleton { NetsurvCompositeEntityStateViewModelFactory(i()) }
    bindSingleton { NetsurvLiveVideoStreamEntityStateViewModelFactory(i()) }
}
