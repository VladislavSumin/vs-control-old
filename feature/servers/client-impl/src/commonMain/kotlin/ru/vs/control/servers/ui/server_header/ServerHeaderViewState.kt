package ru.vs.control.servers.ui.server_header

import ru.vs.control.servers.domain.Server
import ru.vs.control.servers_connection.domain.ServerConnectionInteractor

internal data class ServerHeaderViewState(
    /**
     * Server model
     */
    val server: Server,

    /**
     * Current server connection status
     */
    val connectionStatus: ServerConnectionInteractor.ConnectionStatus,
)
