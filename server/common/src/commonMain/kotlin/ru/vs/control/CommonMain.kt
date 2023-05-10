package ru.vs.control

import io.github.oshai.KotlinLogging
import kotlinx.coroutines.launch
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.control.domain.AboutServerInteractor
import ru.vs.control.service_cams_netsurv.domain.NetsurvCamsService
import ru.vs.control.services.domain.ServicesInteractor
import ru.vs.control.web.WebServer

fun commonMain() {
    val logger = KotlinLogging.logger("main")
    logger.info { "Server starting..." }

    val scope = ServerScope()

    val di = createDiGraph(scope)

    val aboutServerInteractor: AboutServerInteractor by di.instance()
    val webServer: WebServer by di.instance()
    val servicesInteractor: ServicesInteractor by di.instance()

    scope.launch {
        // Print server version
        val version = aboutServerInteractor.getVersion()
        logger.info { "Version: $version" }

        // Register services
        // TODO move this code to another place
        servicesInteractor.registerService(di.direct.instance<NetsurvCamsService>())

        // Run web server
        webServer.run()
    }

    // We don't have to terminate the main thread since all coroutine schedulers use daemon threads
    scope.blockingAwait()
}
