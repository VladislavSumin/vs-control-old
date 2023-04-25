package ru.vs.rsub.connector.ktor_websocket

import io.ktor.routing.Route
import io.ktor.websocket.webSocket
import ru.vs.rsub.RSubServer

fun Route.rSubWebSocket(server: RSubServer, path: String = "/rSub") {
    webSocket(path = path, protocol = "rSub") {
        server.handleNewConnection(RSubConnectionKtorWebSocket(this))
    }
}
