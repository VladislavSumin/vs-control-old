package ru.vs.control.servers_connection.domain

import io.ktor.client.HttpClient
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers_connection.rsub.ServerRSubClient
import ru.vs.control.servers_connection.rsub.ServerRSubClientImpl
import ru.vs.rsub.connector.ktor_websocket.RSubConnectorKtorWebSocket

internal interface ServerConnectionInteractor

internal class ServerConnectionInteractorImpl(
    httpClient: HttpClient,
    server: Server,
) : ServerConnectionInteractor {
    private val client: ServerRSubClient = ServerRSubClientImpl(
        RSubConnectorKtorWebSocket(
            client = httpClient,
            host = server.host,
            port = server.port
        )
    )
}
