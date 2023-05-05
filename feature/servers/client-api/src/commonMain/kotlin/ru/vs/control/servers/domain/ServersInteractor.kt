package ru.vs.control.servers.domain

import kotlinx.coroutines.flow.Flow

interface ServersInteractor {
    fun observeServers(): Flow<List<Server>>
    fun observeSelectedServerId(): Flow<ServerId?>
    fun observeSelectedServer(): Flow<Server?>

    suspend fun setSelectedServer(serverId: ServerId?)
    suspend fun getServer(id: ServerId): Server
    suspend fun findServer(id: ServerId): Server?
    suspend fun addServer(server: Server)
    suspend fun updateServer(server: Server)
    suspend fun deleteServer(id: ServerId)
}
