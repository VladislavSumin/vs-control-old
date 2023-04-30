package ru.vs.control.servers.service

import ru.vs.control.servers.repository.ServerRecordQueries

interface ServerQueriesProvider {
    suspend fun getServerQueries(): ServerRecordQueries
}
