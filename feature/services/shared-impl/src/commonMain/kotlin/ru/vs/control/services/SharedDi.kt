package ru.vs.control.services

import org.kodein.di.DI
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.entities.domain.EntityState
import ru.vs.control.entities.domain.ExternalEntityStateSerializer
import ru.vs.control.services.entity_states.EmptyServiceDescriptionCompositeEntityState
import ru.vs.core.di.Modules

fun Modules.featureServiceShared() = DI.Module("feature-service-shared") {
    inBindSet<ExternalEntityStateSerializer<out EntityState>> {
        add { singleton { ExternalEntityStateSerializer<EmptyServiceDescriptionCompositeEntityState>() } }
    }
}
