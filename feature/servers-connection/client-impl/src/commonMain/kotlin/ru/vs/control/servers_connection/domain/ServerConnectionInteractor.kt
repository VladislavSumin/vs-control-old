package ru.vs.control.servers_connection.domain

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers_connection.rsub.ServerRSubClientImpl
import ru.vs.rsub.RSubConnectionStatus
import ru.vs.rsub.connector.ktor_websocket.RSubConnectorKtorWebSocket

interface ServerConnectionInteractor {
    fun observeConnectionStatus(): Flow<RSubConnectionStatus>
}

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

    override fun observeConnectionStatus(): Flow<RSubConnectionStatus> = client.observeConnectionStatus()
}
