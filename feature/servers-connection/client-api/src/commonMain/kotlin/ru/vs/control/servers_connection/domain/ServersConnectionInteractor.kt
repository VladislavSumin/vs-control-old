package ru.vs.control.servers_connection.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.control.servers.domain.Server

/**
 * Creates and manages instances of [ServerConnectionInteractor]
 */
interface ServersConnectionInteractor {
    /**
     * Returns [ServerConnectionInteractor] witch represents connection with given [server]
     * For same [server] returning same connection
     */
    suspend fun getConnection(server: Server): ServerConnectionInteractor

    /**
     * Observe current selected server and return connection with it
     * If no selected servers emit null value
     */
    fun observeSelectedServerConnection(): Flow<ServerConnectionInteractor?>
}
