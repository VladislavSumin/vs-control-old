package ru.vs.control.servers.ui.server_card

import ru.vs.control.about_server.domain.AboutServerInteractor
import ru.vs.control.servers.domain.Server

internal class ServerCardViewState(
    /**
     * Server model
     */
    val server: Server,

    /**
     * Current server connection status
     */
    val connectionStatus: AboutServerInteractor.ConnectionStatusWithServerInfo,

    /**
     * Is this server selected as default server
     */
    val isSelected: Boolean,
)
