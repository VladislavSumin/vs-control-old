package ru.vs.control.servers_connection.domain

import ru.vs.control.servers.domain.Server

interface ServersConnectionInteractor {
    suspend fun getConnection(server: Server): ServerConnectionInteractor
}
