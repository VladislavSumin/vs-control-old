package ru.vs.control.web

import io.ktor.server.cio.*
import io.ktor.server.engine.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

private const val SERVER_DEFAULT_PORT = 8080


interface WebServer {
    /**
     * Running web server, don't return control while server was running
     */
    suspend fun run()
}

class WebServerImpl : WebServer {
    override suspend fun run() {
        withContext(CoroutineName("web-server")) {
            val environment = createEnvironment()
            val server = createEmbeddedServer(environment)
            server.start(true)
        }
    }

    private fun createEmbeddedServer(environment: ApplicationEngineEnvironment): ApplicationEngine =
        embeddedServer(CIO, environment)

    private fun CoroutineScope.createEnvironment(): ApplicationEngineEnvironment {
        return applicationEngineEnvironment {
            parentCoroutineContext = coroutineContext

            connector {
                host = "0.0.0.0"
                port = SERVER_DEFAULT_PORT
            }

            module {
                // contentNegotiationConfiguration.apply { configure() }
                // routing {
                // }
            }
        }
    }
}