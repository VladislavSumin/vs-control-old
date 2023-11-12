package ru.vs.control.root_navigation

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.root_navigation.ui.RootNavigationComponentFactory
import ru.vs.control.root_navigation.ui.RootNavigationComponentFactoryImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureRootNavigation() = DI.Module("feature-root-navigation") {
    bindSingleton<RootNavigationComponentFactory> { RootNavigationComponentFactoryImpl(i(), i(), i()) }
}
