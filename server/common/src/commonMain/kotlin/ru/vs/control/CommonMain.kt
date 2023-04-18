package ru.vs.control

import io.github.oshai.KotlinLogging
import kotlinx.coroutines.launch
import org.kodein.di.instance
import ru.vs.control.domain.AboutServerInteractor
import ru.vs.control.web.WebServer

fun commonMain() {
    val logger = KotlinLogging.logger("main")
    logger.info { "Server starting..." }

    val scope = ServerScope()
    val aboutServerInteractor: AboutServerInteractor by Di.instance()
    val webServer: WebServer by Di.instance()

    scope.launch {
        // Print server version
        val version = aboutServerInteractor.getVersion()
        logger.info { "Version: $version" }

        // Run web server
        webServer.run()
    }

    // We don't have to terminate the main thread since all coroutine schedulers use daemon threads
    scope.blockingAwait()
}
