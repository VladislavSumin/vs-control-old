package ru.vs.control.about_server.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.control.servers.domain.Server

interface AboutServerInteractor {
    /**
     * Keep server connection open and provide [ServerInfo]
     * @see ConnectionStatusWithServerInfo
     * @param server server to connect
     */
    fun observeConnectionStatusWithServerInfo(server: Server): Flow<ConnectionStatusWithServerInfo>

    /**
     * Request [ServerInfo] from given [Server]
     * @param server server to request [ServerInfo]
     * @return information about given [Server]
     */
    suspend fun getServerInfo(server: Server): ServerInfo

    /**
     * Represent current connection state
     */
    sealed interface ConnectionStatusWithServerInfo {
        data object Connecting : ConnectionStatusWithServerInfo
        data class Connected(val serverInfo: ServerInfo) : ConnectionStatusWithServerInfo
        data class FailedToGetServerInfo(val error: Throwable) : ConnectionStatusWithServerInfo
        data class Reconnecting(val connectionError: Exception) : ConnectionStatusWithServerInfo
    }
}
