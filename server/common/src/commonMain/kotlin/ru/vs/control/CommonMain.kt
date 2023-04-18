package ru.vs.control

import io.github.oshai.KotlinLogging
import kotlinx.coroutines.launch
import org.kodein.di.instance
import ru.vs.control.domain.AboutServerInteractor

fun commonMain() {
    val logger = KotlinLogging.logger("main")
    logger.info { "Server starting..." }

    val scope = ServerScope()
    val aboutServerInteractor by Di.instance<AboutServerInteractor>()

    scope.launch {
        val version = aboutServerInteractor.getVersion()
        logger.info { "Version: $version" }
        println("Hello server")
    }

    scope.blockingAwait()
}
