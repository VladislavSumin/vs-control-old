package ru.vs.control.servers_connection.domain

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers_connection.rsub.ServerRSubClientImpl
import ru.vs.rsub.RSubConnectionStatus
import ru.vs.rsub.connector.ktor_websocket.RSubConnectorKtorWebSocket

internal class ServerConnectionInteractorImpl(
    httpClient: HttpClient,
    server: Server,
) : ServerConnectionInteractor {
    private val client: ServerRSubClientImpl = ServerRSubClientImpl(
        RSubConnectorKtorWebSocket(
            client = httpClient,
            host = server.host,
            port = server.port
        )
    )

    override fun observeConnectionStatus(): Flow<Boolean> = client.observeConnectionStatus().map {
        it == RSubConnectionStatus.CONNECTED
    }
}
