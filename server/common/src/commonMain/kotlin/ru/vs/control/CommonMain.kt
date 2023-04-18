package ru.vs.control

import io.github.oshai.KotlinLogging

fun commonMain() {
    val logger = KotlinLogging.logger("main")
    logger.info { "Server starting..." }
    logger.info { "Version: ${BuildConfig.version}" }
    val scope = ServerScope()
    println("Hello server")
    scope.blockingAwait()
}
