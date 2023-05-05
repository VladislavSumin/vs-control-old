package ru.vs.control.entities

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.entities.domain.EntitiesInteractorImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureEntities() = DI.Module("feature-entities") {
    bindSingleton<EntitiesInteractor> { EntitiesInteractorImpl(i()) }
}
