package ru.vs.control.services

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.services.ui.services.ServicesComponentFactory
import ru.vs.control.services.ui.services.ServicesComponentFactoryImpl
import ru.vs.core.di.Modules

fun Modules.featureServices() = DI.Module("feature-services") {
    bindSingleton<ServicesComponentFactory> { ServicesComponentFactoryImpl() }
}
