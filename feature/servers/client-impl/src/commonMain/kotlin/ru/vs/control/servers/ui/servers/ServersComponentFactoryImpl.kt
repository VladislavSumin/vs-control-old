package ru.vs.control.servers.ui.servers

import ru.vs.control.servers.domain.ServerId
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

internal class ServersComponentFactoryImpl : ServersComponentFactory {
    override fun create(
        diComponentContext: DiComponentContext,
        openAddServerScreen: () -> Unit,
        openEditServerScreen: (ServerId) -> Unit,
    ): ComposeComponent {
        return ServersComponent(diComponentContext, openAddServerScreen, openEditServerScreen)
    }
}
