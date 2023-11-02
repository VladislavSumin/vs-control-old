package ru.vs.control.service_cams_netsurv

import org.kodein.di.DI
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.entities.domain.EntityState
import ru.vs.control.entities.domain.ExternalEntityStateSerializer
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvCompositeEntityState
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvLiveVideoStreamEntityState
import ru.vs.core.di.Modules

fun Modules.featureServiceCamsNetsurvShared() = DI.Module("feature-service-cams-netsurv-shared") {
    inBindSet<ExternalEntityStateSerializer<out EntityState>> {
        add { singleton { ExternalEntityStateSerializer<NetsurvCompositeEntityState>() } }
        add { singleton { ExternalEntityStateSerializer<NetsurvLiveVideoStreamEntityState>() } }
    }
}
