package ru.vs.control.servers.ui.edit_server

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

interface EditServerComponentFactory {
    fun create(
        context: ComponentContext,
        serverId: Long?,
        closeScreen: () -> Unit,
    ): ComposeComponent
}
