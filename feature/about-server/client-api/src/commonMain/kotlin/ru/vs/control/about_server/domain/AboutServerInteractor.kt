package ru.vs.control.about_server.domain

import ru.vs.control.servers.domain.Server

interface AboutServerInteractor {
    suspend fun getServerInfo(server: Server): ServerInfo
}
