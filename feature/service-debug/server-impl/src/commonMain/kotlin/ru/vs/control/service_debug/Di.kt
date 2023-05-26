package ru.vs.control.service_debug

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.service_debug.domain.DebugServiceImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureServiceDebug() = DI.Module("feature-service-debug") {
    bindSingleton { DebugServiceImpl(i()) }
}
