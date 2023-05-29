package ru.vs.control.entities

import org.kodein.di.DI
import org.kodein.di.bindSet
import org.kodein.di.bindSingleton
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.entities.domain.EntitiesInteractorImpl
import ru.vs.control.entities.ui.entities.EntitiesComponentFactory
import ru.vs.control.entities.ui.entities.EntitiesComponentFactoryImpl
import ru.vs.control.entities.ui.entities.EntitiesViewModelFactory
import ru.vs.control.entities.ui.entities.boolean_entity_state.BooleanEntityStateComponentFactory
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactory
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactoryRegistry
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactoryRegistryImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesShared())

    bindSet<EntityStateComponentFactory<*>>()
    inBindSet<EntityStateComponentFactory<*>> {
        add { singleton { BooleanEntityStateComponentFactory() } }
    }

    // Interactors
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i(), i()) }

    // View model factories
    bindSingleton { EntitiesViewModelFactory(i()) }

    // Component factories
    bindSingleton<EntitiesComponentFactory> { EntitiesComponentFactoryImpl(i(), i()) }

    // Other
    bindSingleton<EntityStateComponentFactoryRegistry> { EntityStateComponentFactoryRegistryImpl(i()) }
}
