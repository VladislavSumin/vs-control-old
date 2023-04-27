package ru.vs.control.servers.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.vs.control.servers.repository.ServersRepository

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

internal class ServersInteractorImpl(
    private val serversRepository: ServersRepository,
) : ServersInteractor {

    override fun observeServers() = serversRepository.observeServers()

    override suspend fun addServer(server: Server) {
        serversRepository.insert(server)
    }
}
