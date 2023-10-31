package ru.vs.control.about_server

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.about_server.rsub.AboutServerRSub
import ru.vs.control.about_server.rsub.AboutServerRSubImpl
import ru.vs.core.di.Modules

fun Modules.featureAboutServer() = DI.Module("feature-about-server") {
    bindSingleton<AboutServerRSub> { AboutServerRSubImpl() }
}
