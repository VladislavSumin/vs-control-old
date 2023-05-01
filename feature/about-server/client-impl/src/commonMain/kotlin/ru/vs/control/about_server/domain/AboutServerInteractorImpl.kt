package ru.vs.control.about_server.domain

import ru.vs.control.about_server.rsub.ServerInfoDto
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers_connection.domain.ServersConnectionInteractor

internal class AboutServerInteractorImpl(
    private val serversConnectionInteractor: ServersConnectionInteractor,
) : AboutServerInteractor {
    override suspend fun getServerInfo(server: Server): ServerInfo {
        return serversConnectionInteractor.getConnection(server).aboutServer.getServerInfo().toServerInfo()
    }
}

private fun ServerInfoDto.toServerInfo(): ServerInfo = ServerInfo(version = version)
