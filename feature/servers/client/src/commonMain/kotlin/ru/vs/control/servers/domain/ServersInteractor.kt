package ru.vs.control.servers.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal interface ServersInteractor {
    fun observeServers(): Flow<List<Server>>
    suspend fun addServer(server: Server)
}

/**
 * Remote Control server info
 */
data class Server(
    /**
     * Unique server id for store it in local database
     */
    val id: Long,

    /**
     * Human-readable server name visible by user
     */
    val name: String,

    /**
     * Connection url
     */
    val url: String,
)

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
