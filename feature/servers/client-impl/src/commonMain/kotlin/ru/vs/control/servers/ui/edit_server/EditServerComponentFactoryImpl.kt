package ru.vs.control.servers.ui.edit_server

import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

internal class EditServerComponentFactoryImpl : EditServerComponentFactory {
    override fun create(context: DiComponentContext, serverId: Long?, closeScreen: () -> Unit): ComposeComponent {
        return EditServerComponent(context, serverId, closeScreen)
    }
}
