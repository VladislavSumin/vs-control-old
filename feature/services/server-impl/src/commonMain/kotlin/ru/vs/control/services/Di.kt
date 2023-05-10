package ru.vs.control.services

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.services.domain.ServicesInteractor
import ru.vs.control.services.domain.ServicesInteractorImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureServices() = DI.Module("feature-services") {
    bindSingleton<ServicesInteractor> { ServicesInteractorImpl(i()) }
}
