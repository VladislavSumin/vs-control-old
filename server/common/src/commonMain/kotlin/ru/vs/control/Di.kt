package ru.vs.control

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.domain.AboutServerInteractor
import ru.vs.control.domain.AboutServerInteractorImpl
import ru.vs.control.web.WebServer
import ru.vs.control.web.WebServerImpl

val Di = DI {
    bindSingleton<AboutServerInteractor> { AboutServerInteractorImpl() }
    bindSingleton<WebServer> { WebServerImpl() }
}
