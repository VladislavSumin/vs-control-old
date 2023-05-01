package ru.vs.control.servers_connection.domain

import kotlinx.coroutines.flow.Flow

interface ServerConnectionInteractor {
    fun observeConnectionStatus(): Flow<ConnectionStatus>

    sealed interface ConnectionStatus {
        /**
         * Initial connection status, this means it's first connection attempt
         */
        object Connecting : ConnectionStatus

        /**
         * Successfully connected
         */
        object Connected : ConnectionStatus

        /**
         * Reconnecting (or waits reconnect timeout) after connection failed
         * @param connectionError - last connection try error
         */
        data class Reconnecting(val connectionError: Exception) : ConnectionStatus
    }
}
