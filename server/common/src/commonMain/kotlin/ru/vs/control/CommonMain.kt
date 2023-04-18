package ru.vs.control

import io.github.oshai.KotlinLogging

fun commonMain() {
    val logger = KotlinLogging.logger("main")
    logger.info { "Server starting..." }
    val scope = ServerScope()
    println("Hello server")
    scope.blockingAwait()
}
