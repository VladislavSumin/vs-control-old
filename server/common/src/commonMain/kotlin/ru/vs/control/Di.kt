package ru.vs.control

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.about_server.featureAboutServer
import ru.vs.control.domain.AboutServerInteractor
import ru.vs.control.domain.AboutServerInteractorImpl
import ru.vs.control.domain.SentryInteractor
import ru.vs.control.domain.SentryInteractorImpl
import ru.vs.control.entities.featureEntities
import ru.vs.control.rsub.RSubServerFactory
import ru.vs.control.rsub.RSubServerFactoryImpl
import ru.vs.control.service_cams_netsurv.featureServiceCamsNetsurv
import ru.vs.control.service_debug.featureServiceDebug
import ru.vs.control.services.featureServices
import ru.vs.control.web.WebServer
import ru.vs.control.web.WebServerImpl
import ru.vs.core.analytic.sentry.coreAnalyticSentry
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.ktor_network.coreKtorNetwork
import ru.vs.core.network.coreNetwork
import ru.vs.core.serialization.json.coreSerializationJson

fun createDiGraph(serverScope: CoroutineScope) = DI.lazy {
    importOnce(Modules.coreAnalyticSentry())
    importOnce(Modules.coreKtorNetwork())
    importOnce(Modules.coreNetwork())
    importOnce(Modules.coreSerializationJson())

    importOnce(Modules.featureAboutServer())
    importOnce(Modules.featureEntities())
    importOnce(Modules.featureServiceCamsNetsurv())
    importOnce(Modules.featureServiceDebug())
    importOnce(Modules.featureServices())

    bindSingleton { serverScope }

    bindSingleton<AboutServerInteractor> { AboutServerInteractorImpl() }
    bindSingleton<SentryInteractor> { SentryInteractorImpl(i()) }

    bindSingleton<WebServer> { WebServerImpl(i()) }
    bindSingleton<RSubServerFactory> { RSubServerFactoryImpl(i(), i(), i(), i()) }
}
