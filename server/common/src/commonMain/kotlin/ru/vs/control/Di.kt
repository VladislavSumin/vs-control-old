package ru.vs.control

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.domain.AboutServerInteractor
import ru.vs.control.domain.AboutServerInteractorImpl
import ru.vs.control.web.WebServer
import ru.vs.control.web.WebServerImpl
import ru.vs.control.web.rsub.RSubServerFactory
import ru.vs.control.web.rsub.RSubServerFactoryImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.serialization.json.coreSerializationJson

val Di = DI.lazy {
    importOnce(Modules.coreSerializationJson())

    bindSingleton<AboutServerInteractor> { AboutServerInteractorImpl() }

    bindSingleton<WebServer> { WebServerImpl(i()) }
    bindSingleton<RSubServerFactory> { RSubServerFactoryImpl() }
}
