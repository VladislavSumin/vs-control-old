package ru.vs.control.about_server.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.control.servers.domain.Server

interface AboutServerInteractor {
    suspend fun observeConnectionStatusWithServerInfo(server: Server): Flow<ConnectionStatusWithServerInfo>
    suspend fun getServerInfo(server: Server): ServerInfo

    sealed interface ConnectionStatusWithServerInfo {
        object Connecting : ConnectionStatusWithServerInfo
        data class Connected(val serverInfo: ServerInfo) : ConnectionStatusWithServerInfo
        data class FailedToGetServerInfo(val error: Throwable) : ConnectionStatusWithServerInfo
        data class Reconnecting(val connectionError: Exception) : ConnectionStatusWithServerInfo
    }
}
