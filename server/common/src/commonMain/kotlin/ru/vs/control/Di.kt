package ru.vs.control

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.about_server.featureAboutServer
import ru.vs.control.domain.AboutServerInteractor
import ru.vs.control.domain.AboutServerInteractorImpl
import ru.vs.control.rsub.RSubServerFactory
import ru.vs.control.rsub.RSubServerFactoryImpl
import ru.vs.control.web.WebServer
import ru.vs.control.web.WebServerImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.serialization.json.coreSerializationJson

val Di = DI.lazy {
    importOnce(Modules.coreSerializationJson())
    importOnce(Modules.featureAboutServer())

    bindSingleton<AboutServerInteractor> { AboutServerInteractorImpl() }

    bindSingleton<WebServer> { WebServerImpl(i()) }
    bindSingleton<RSubServerFactory> { RSubServerFactoryImpl(i()) }
}
