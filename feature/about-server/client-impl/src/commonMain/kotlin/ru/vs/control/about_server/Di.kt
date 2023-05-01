package ru.vs.control.about_server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.about_server.domain.AboutServerInteractor
import ru.vs.control.about_server.domain.AboutServerInteractorImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureAboutServer() = DI.Module("feature-about-sever") {
    bindSingleton<AboutServerInteractor> { AboutServerInteractorImpl(i()) }
}
