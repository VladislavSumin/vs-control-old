package ru.vs.control.servers.ui.servers

import ru.vs.control.servers.domain.ServerId
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

interface ServersComponentFactory {
    fun create(
        diComponentContext: DiComponentContext,
        openAddServerScreen: () -> Unit,
        openEditServerScreen: (ServerId) -> Unit,
    ): ComposeComponent
}
