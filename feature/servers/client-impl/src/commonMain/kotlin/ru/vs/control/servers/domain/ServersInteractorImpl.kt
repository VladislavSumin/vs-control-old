package ru.vs.control.servers.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import ru.vs.control.servers.repository.SelectedServerRepository
import ru.vs.control.servers.repository.ServersRepository

internal class ServersInteractorImpl(
    private val serversRepository: ServersRepository,
    private val selectedServerRepository: SelectedServerRepository,
) : ServersInteractor {
    override fun observeServers() = serversRepository.observeServers()
    override fun observeSelectedServerId(): Flow<ServerId?> = selectedServerRepository.observeSelectedServer()

    override fun observeSelectedServer(): Flow<Server?> {
        return observeSelectedServerId().flatMapLatest { serverId ->
            if (serverId != null) flow { emit(findServer(serverId)) }
            else flowOf(null)
        }
    }

    override suspend fun setSelectedServer(serverId: ServerId?) = selectedServerRepository.setSelectedServer(serverId)
    override suspend fun getServer(id: ServerId): Server = serversRepository.get(id)
    override suspend fun findServer(id: ServerId): Server? = serversRepository.find(id)
    override suspend fun addServer(server: Server) = serversRepository.insert(server)
    override suspend fun updateServer(server: Server) = serversRepository.update(server)
    override suspend fun deleteServer(id: ServerId) = serversRepository.delete(id)
}
