package ru.vs.control.servers.ui.servers

import com.arkivanov.decompose.ComponentContext
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.ui.server_card.ServerCardComponentFactory
import ru.vs.core.decompose.ComposeComponent

internal class ServersComponentFactoryImpl(
    private val serverCardComponentFactory: ServerCardComponentFactory,
    private val serverStoreFactory: ServerStoreFactory,
) : ServersComponentFactory {
    override fun create(
        openAddServerScreen: () -> Unit,
        openEditServerScreen: (ServerId) -> Unit,
        context: ComponentContext,
    ): ComposeComponent {
        return ServersComponent(
            serverCardComponentFactory,
            serverStoreFactory,
            openAddServerScreen,
            openEditServerScreen,
            context,
        )
    }
}
