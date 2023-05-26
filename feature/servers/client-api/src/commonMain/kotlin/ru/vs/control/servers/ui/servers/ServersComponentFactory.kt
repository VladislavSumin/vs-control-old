package ru.vs.control.servers.ui.servers

import com.arkivanov.decompose.ComponentContext
import ru.vs.control.servers.domain.ServerId
import ru.vs.core.decompose.ComposeComponent

interface ServersComponentFactory {
    fun create(
        openAddServerScreen: () -> Unit,
        openEditServerScreen: (ServerId) -> Unit,
        context: ComponentContext,
    ): ComposeComponent
}
