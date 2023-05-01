package ru.vs.control.servers.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.service.ServerQueriesProvider

internal interface ServersRepository {
    fun observeServers(): Flow<List<Server>>
    suspend fun insert(server: Server)
    suspend fun delete(serverId: ServerId)
}

internal class ServersRepositoryImpl(private val serverQueriesProvider: ServerQueriesProvider) : ServersRepository {
    override fun observeServers(): Flow<List<Server>> {
        return flow { emit(serverQueriesProvider.getServerQueries()) }
            .flatMapLatest { serverRecordQuery ->
                serverRecordQuery.selectAll()
                    .asFlow()
                    .mapToList(Dispatchers.Default)
                    .map { it.toServers() }
            }
    }

    override suspend fun insert(server: Server) = withContext(Dispatchers.Default) {
        check(server.id == 0L)
        serverQueriesProvider.getServerQueries().insert(server.toRecord())
    }

    override suspend fun delete(serverId: ServerId) = withContext(Dispatchers.Default) {
        serverQueriesProvider.getServerQueries().delete(serverId)
    }
}

private fun List<ServerRecord>.toServers(): List<Server> = map { it.toServer() }
private fun ServerRecord.toServer(): Server = Server(id, name, url)
private fun Server.toRecord(): ServerRecord = ServerRecord(id, name, url)
