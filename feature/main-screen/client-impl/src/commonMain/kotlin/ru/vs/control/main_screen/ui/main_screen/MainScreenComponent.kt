package ru.vs.control.main_screen.ui.main_screen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.control.entities.ui.entities.EntitiesComponent
import ru.vs.control.root_navigation.ui.RootNavigationConfig
import ru.vs.control.servers.ui.servers.ServersComponent
import ru.vs.control.services.ui.services.ServicesComponent
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

class MainScreenComponent(
    context: DiComponentContext,
    private val rootNavigation: StackNavigation<RootNavigationConfig>
) : ComposeComponent, DiComponentContext by context {
    private val navigation = StackNavigation<Config>()

    private val internalStack: Value<ChildStack<Config, ComposeComponent>> =
        childStack<Config, ComposeComponent>(
            source = navigation,
            initialConfiguration = Config.Servers,
            handleBackButton = true,
            childFactory = ::child
        )

    internal val stack: Value<ChildStack<*, ComposeComponent>> = internalStack
    internal val selectedDrawerElement = internalStack.map { it.active.configuration.drawerElement }

    internal fun onSelectDrawerElement(drawerElement: DrawerElement) {
        when (drawerElement) {
            DrawerElement.Entities -> navigation.navigate { listOf(Config.Entities) }
            DrawerElement.Services -> navigation.navigate { listOf(Config.Services) }
            DrawerElement.Servers -> navigation.navigate { listOf(Config.Servers) }
        }
    }

    private fun child(config: Config, componentContext: ComponentContext): ComposeComponent {
        val diComponentContext = DiComponentContext(componentContext, di)
        return when (config) {
            is Config.Entities -> entitiesComponent(diComponentContext)
            is Config.Services -> servicesComponent(diComponentContext)
            is Config.Servers -> serversComponent(diComponentContext)
        }
    }

    private fun entitiesComponent(componentContext: DiComponentContext): EntitiesComponent {
        return EntitiesComponent(componentContext)
    }

    private fun servicesComponent(componentContext: DiComponentContext): ServicesComponent {
        return ServicesComponent(componentContext)
    }

    private fun serversComponent(componentContext: DiComponentContext): ServersComponent {
        return ServersComponent(
            componentContext,
            openAddServerScreen = { rootNavigation.push(RootNavigationConfig.EditServer(null)) },
            openEditServerScreen = { serverId -> rootNavigation.push(RootNavigationConfig.EditServer(serverId)) },
        )
    }

    @Composable
    override fun Render() = MainScreenContent(this)

    private val Config.drawerElement: DrawerElement
        get() = when (this) {
            Config.Entities -> DrawerElement.Entities
            Config.Services -> DrawerElement.Services
            Config.Servers -> DrawerElement.Servers
        }

    @Parcelize
    private sealed interface Config : Parcelable {
        object Entities : Config
        object Services : Config
        object Servers : Config
    }
}
