package ru.vs.control.servers_connection.domain

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers_connection.rsub.ServerRSubClientImpl
import ru.vs.rsub.RSubConnectionStatus
import ru.vs.rsub.connector.ktor_websocket.RSubConnectorKtorWebSocket

internal class ServerConnectionInteractorImpl(
    httpClient: HttpClient,
    json: Json,
    server: Server,
) : ServerConnectionInteractor {
    private val client: ServerRSubClientImpl = ServerRSubClientImpl(
        RSubConnectorKtorWebSocket(
            client = httpClient,
            host = server.host,
            port = server.port,
        ),
        json = json,
    )

    override fun observeConnectionStatus(): Flow<ServerConnectionInteractor.ConnectionStatus> =
        client.observeConnectionStatus().map { it.toConnectionStatus() }
}

private fun RSubConnectionStatus.toConnectionStatus(): ServerConnectionInteractor.ConnectionStatus {
    return when (this) {
        RSubConnectionStatus.Connected -> ServerConnectionInteractor.ConnectionStatus.Connected
        RSubConnectionStatus.Connecting -> ServerConnectionInteractor.ConnectionStatus.Connecting
        is RSubConnectionStatus.Reconnecting -> ServerConnectionInteractor.ConnectionStatus.Reconnecting(connectionError)
    }
}
