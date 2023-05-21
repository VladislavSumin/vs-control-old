package ru.vs.control.main_screen

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.main_screen.ui.main_screen.MainScreenComponentFactory
import ru.vs.control.main_screen.ui.main_screen.MainScreenComponentFactoryImpl
import ru.vs.core.di.Modules

fun Modules.featureMainScreen() = DI.Module("feature-main-screen") {
    bindSingleton<MainScreenComponentFactory> { MainScreenComponentFactoryImpl() }
}
