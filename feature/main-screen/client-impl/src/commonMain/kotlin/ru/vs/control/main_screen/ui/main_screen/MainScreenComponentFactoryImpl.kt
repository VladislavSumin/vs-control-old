package ru.vs.control.main_screen.ui.main_screen

import com.arkivanov.decompose.router.stack.StackNavigation
import ru.vs.control.entities.ui.entities.EntitiesComponentFactory
import ru.vs.control.root_navigation.ui.RootNavigationConfig
import ru.vs.control.servers.ui.servers.ServersComponentFactory
import ru.vs.control.services.ui.services.ServicesComponentFactory
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

internal class MainScreenComponentFactoryImpl(
    private val entitiesComponentFactory: EntitiesComponentFactory,
    private val serversComponentFactory: ServersComponentFactory,
    private val servicesComponentFactory: ServicesComponentFactory,
) : MainScreenComponentFactory {
    override fun create(
        context: DiComponentContext,
        rootNavigation: StackNavigation<RootNavigationConfig>
    ): ComposeComponent {
        return MainScreenComponent(
            entitiesComponentFactory,
            serversComponentFactory,
            servicesComponentFactory,
            context,
            rootNavigation,
        )
    }
}
