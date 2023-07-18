package ru.vs.control.servers.ui.edit_server

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

internal class EditServerComponentFactoryImpl(
    private val editServerStoreFactory: EditServerStoreFactory,
) : EditServerComponentFactory {
    override fun create(context: ComponentContext, serverId: Long?, closeScreen: () -> Unit): ComposeComponent {
        return EditServerComponent(editServerStoreFactory, context, serverId, closeScreen)
    }
}
