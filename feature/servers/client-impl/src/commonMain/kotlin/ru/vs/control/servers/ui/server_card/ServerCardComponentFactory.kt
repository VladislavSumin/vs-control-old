package ru.vs.control.servers.ui.server_card

import com.arkivanov.decompose.ComponentContext
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId

internal class ServerCardComponentFactory(
    private val serverCardStoreFactory: ServerCardStoreFactory
) {
    fun create(
        server: Server,
        openEditServerScreen: (ServerId) -> Unit,
        context: ComponentContext,
    ): ServerCardComponent {
        return ServerCardComponent(serverCardStoreFactory, server, openEditServerScreen, context)
    }
}
