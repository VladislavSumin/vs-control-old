package ru.vs.control.servers.ui.edit_server

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

interface EditServerComponentFactory {
    /**
     * @param serverId for edit mode pass here [serverId]. If [serverId] not passed, then we are in new server creating mode
     * @param closeScreen callback calling when this screen want to be closed
     */
    fun create(
        context: ComponentContext,
        serverId: Long? = null,
        closeScreen: () -> Unit,
    ): ComposeComponent
}
