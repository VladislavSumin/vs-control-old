package ru.vs.control

import io.github.oshai.KotlinLogging
import kotlinx.coroutines.launch
import ru.vs.control.domain.AboutServerInteractorImpl

fun commonMain() {
    val logger = KotlinLogging.logger("main")
    logger.info { "Server starting..." }
    val scope = ServerScope()
    scope.launch {
        val aboutServerInteractor = AboutServerInteractorImpl()
        val version = aboutServerInteractor.getVersion()
        logger.info { "Version: $version" }
        println("Hello server")
    }
    scope.blockingAwait()
}
