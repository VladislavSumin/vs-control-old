package ru.vs.control.servers_connection.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.control.about_server.rsub.AboutServerRSub
import ru.vs.control.entities.rsub.EntitiesRsub
import ru.vs.control.servers.domain.Server
import ru.vs.control.service_cams_netsurv.rsub.NetsurvCamsRsub

interface ServerConnectionInteractor {
    /**
     * server endpoint for current instance of [ServersConnectionInteractor]
     */
    val server: Server

    val aboutServer: AboutServerRSub
    val entities: EntitiesRsub
    val netsurvCams: NetsurvCamsRsub

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
