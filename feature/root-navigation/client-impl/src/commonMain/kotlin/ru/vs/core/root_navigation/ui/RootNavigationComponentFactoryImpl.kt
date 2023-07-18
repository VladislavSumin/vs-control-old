package ru.vs.core.root_navigation.ui

import com.arkivanov.decompose.ComponentContext
import ru.vs.control.main_screen.ui.main_screen.MainScreenComponentFactory
import ru.vs.control.root_navigation.ui.RootNavigationComponentFactory
import ru.vs.control.servers.ui.edit_server.EditServerComponentFactory
import ru.vs.core.decompose.ComposeComponent

internal class RootNavigationComponentFactoryImpl(
    private val mainScreenComponentFactory: MainScreenComponentFactory,
    private val editServerComponentFactory: EditServerComponentFactory,
) : RootNavigationComponentFactory {
    override fun create(context: ComponentContext): ComposeComponent {
        return RootNavigationComponent(mainScreenComponentFactory, editServerComponentFactory, context)
    }
}
