package ru.vs.control.servers.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal interface ServersInteractor {
    fun observeServers(): Flow<List<Server>>
    suspend fun addServer(server: Server)
}

data class Server(val name: String)

internal class ServersInteractorImpl : ServersInteractor {
    private val servers: MutableStateFlow<List<Server>> = MutableStateFlow(emptyList())

    override fun observeServers(): Flow<List<Server>> {
        return servers
    }

    override suspend fun addServer(server: Server) {
        servers.update {
            it + server
        }
    }
}
