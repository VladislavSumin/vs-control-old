package ru.vs.control.entities

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.entities.rsub.EntitiesRsub
import ru.vs.control.entities.rsub.EntitiesRsubImpl
import ru.vs.core.di.Modules

fun Modules.featureEntities() = DI.Module("feature-entities") {
    bindSingleton<EntitiesRsub> { EntitiesRsubImpl() }
}
