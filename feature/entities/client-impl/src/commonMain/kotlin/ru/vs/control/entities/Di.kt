package ru.vs.control.entities

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.entities.domain.EntitiesInteractorImpl
import ru.vs.control.entities.ui.entities.EntitiesComponentFactory
import ru.vs.control.entities.ui.entities.EntitiesComponentFactoryImpl
import ru.vs.control.entities.ui.entities.EntitiesStoreFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureEntities() = DI.Module("feature-entities") {
    importOnce(Modules.featureEntitiesShared())

    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i(), i()) }
    bindSingleton { EntitiesStoreFactory(i(), i()) }
    bindSingleton<EntitiesComponentFactory> { EntitiesComponentFactoryImpl(i()) }
}
