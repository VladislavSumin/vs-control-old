package ru.vs.control.servers.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.vs.control.servers.domain.Server

internal interface ServersRepository {
    fun observeServers(): Flow<List<Server>>
    suspend fun insert(server: Server)
    suspend fun delete(server: Server)
}

internal class ServersRepositoryImpl(private val serverQueries: ServerRecordQueries) : ServersRepository {
    override fun observeServers(): Flow<List<Server>> {
        return serverQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { it.toServers() }
    }

    override suspend fun insert(server: Server) = withContext(Dispatchers.Default) {
        check(server.id == 0L)
        serverQueries.insert(server.toRecord())
    }

    override suspend fun delete(server: Server) = withContext(Dispatchers.Default) {
        serverQueries.delete(server.id)
    }
}

private fun List<ServerRecord>.toServers(): List<Server> = map { it.toServer() }
private fun ServerRecord.toServer(): Server = Server(id, name, url)
private fun Server.toRecord(): ServerRecord = ServerRecord(id, name, url)
