package ru.vs.control.web

import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import ru.vs.control.platform.platformStaticResources
import ru.vs.control.rsub.RSubServerFactory
import ru.vs.rsub.connector.ktor_websocket.rSubWebSocket

private const val SERVER_DEFAULT_PORT = 8080

internal interface WebServer {
    /**
     * Running web server, don't return control while server was running
     */
    suspend fun run()
}

internal class WebServerImpl(
    private val rSubServerFactory: RSubServerFactory,
) : WebServer {
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
                install(WebSockets)

                routing {
                    // Share static resources bundled inside server app
                    this.platformStaticResources("/", "resources/web")

                    // Setup rSub protocol
                    rSubWebSocket(rSubServerFactory.create())
                }
            }
        }
    }
}
