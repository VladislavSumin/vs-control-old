package ru.vs.control.about_server.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.vs.control.about_server.domain.AboutServerInteractor.ConnectionStatusWithServerInfo
import ru.vs.control.about_server.rsub.ServerInfoDto
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers_connection.domain.ServerConnectionInteractor.ConnectionStatus
import ru.vs.control.servers_connection.domain.ServersConnectionInteractor

internal class AboutServerInteractorImpl(
    private val serversConnectionInteractor: ServersConnectionInteractor,
) : AboutServerInteractor {
    override fun observeConnectionStatusWithServerInfo(
        server: Server
    ): Flow<ConnectionStatusWithServerInfo> {
        return flow { emit(serversConnectionInteractor.getConnection(server)) }
            .flatMapLatest { it.observeConnectionStatus() }
            .mapLatest { connectionStatus ->
                when (connectionStatus) {
                    ConnectionStatus.Connected -> {
                        runCatching {
                            getServerInfo(server)
                        }
                            .map { serverInfo -> ConnectionStatusWithServerInfo.Connected(serverInfo) }
                            .getOrElse { ConnectionStatusWithServerInfo.FailedToGetServerInfo(it) }
                    }

                    ConnectionStatus.Connecting ->
                        ConnectionStatusWithServerInfo.Connecting

                    is ConnectionStatus.Reconnecting ->
                        ConnectionStatusWithServerInfo.Reconnecting(connectionStatus.connectionError)
                }
            }
    }

    override suspend fun getServerInfo(server: Server): ServerInfo {
        return serversConnectionInteractor.getConnection(server).aboutServer.getServerInfo().toServerInfo()
    }
}

private fun ServerInfoDto.toServerInfo(): ServerInfo = ServerInfo(version = version)
