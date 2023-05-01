package ru.vs.control.servers.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.control.servers.repository.ServersRepository

internal interface ServersInteractor {
    fun observeServers(): Flow<List<Server>>
    suspend fun getServer(id: ServerId): Server
    suspend fun addServer(server: Server)
    suspend fun updateServer(server: Server)
    suspend fun deleteServer(id: ServerId)
}

internal class ServersInteractorImpl(
    private val serversRepository: ServersRepository,
) : ServersInteractor {
    override fun observeServers() = serversRepository.observeServers()
    override suspend fun getServer(id: ServerId): Server = serversRepository.get(id)
    override suspend fun addServer(server: Server) = serversRepository.insert(server)
    override suspend fun updateServer(server: Server) = serversRepository.update(server)
    override suspend fun deleteServer(id: ServerId) = serversRepository.delete(id)
}
