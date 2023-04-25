@file:Suppress("MagicNumber")

import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.vs.rsub.RSubServer
import ru.vs.rsub.connector.ktor_websocket.RSubConnectorKtorWebSocket
import ru.vs.rsub.connector.ktor_websocket.rSubWebSocket
import ru.vs.rsub.playground.TestClient
import ru.vs.rsub.playground.TestClientImpl
import ru.vs.rsub.playground.TestInterfaceImpl
import ru.vs.rsub.playground.TestServerSubscriptionsImpl

fun main() {
    runClientSever()
}

private fun runClientSever() {
    val server = startServer()
    val httpClient = createHttpClient()
    val rSubClient = createRSubClient(httpClient)

    runBlocking {
        withContext(Dispatchers.IO) {
//        val job = launch {
//            rSubClient.observeConnection().collect {
//                println("New status $it")
//            }
//        }

            println(rSubClient.test.testString())
        }
    }

    httpClient.close()
    server.stop(100L, 100L)
}

private fun startServer(): NettyApplicationEngine {
    val impls = TestServerSubscriptionsImpl(TestInterfaceImpl())
    val rSubServer = RSubServer(impls)
    return embeddedServer(Netty, port = 8080) {
        install(io.ktor.websocket.WebSockets)
        routing {
            rSubWebSocket(rSubServer)
        }
    }.start(false)
}

private fun createHttpClient(): HttpClient {
    return HttpClient {
        install(io.ktor.client.features.websocket.WebSockets)
    }
}

private fun createRSubClient(client: HttpClient): TestClient {
    return TestClientImpl(RSubConnectorKtorWebSocket(client))
}
