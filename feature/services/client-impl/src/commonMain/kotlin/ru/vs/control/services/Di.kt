package ru.vs.control.services

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactory
import ru.vs.control.services.ui.service_description_composite_entity_state.SimpleServiceDescriptionCompositeEntityStateComponentFactory
import ru.vs.control.services.ui.services.ServicesComponentFactory
import ru.vs.control.services.ui.services.ServicesComponentFactoryImpl
import ru.vs.core.di.Modules

fun Modules.featureServices() = DI.Module("feature-services") {
    importOnce(Modules.featureServiceShared())

    inBindSet<EntityStateComponentFactory<*>> {
        add { singleton { SimpleServiceDescriptionCompositeEntityStateComponentFactory() } }
    }

    bindSingleton<ServicesComponentFactory> { ServicesComponentFactoryImpl() }
}
