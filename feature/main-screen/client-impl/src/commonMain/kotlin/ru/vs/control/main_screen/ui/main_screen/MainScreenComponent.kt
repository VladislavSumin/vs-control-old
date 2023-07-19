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
import ru.vs.control.entities.ui.entities.EntitiesComponentFactory
import ru.vs.control.root_navigation.ui.RootNavigationConfig
import ru.vs.control.servers.ui.servers.ServersComponentFactory
import ru.vs.control.services.ui.services.ServicesComponentFactory
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(MainScreenComponentFactory::class)
internal class MainScreenComponent(
    private val entitiesComponentFactory: EntitiesComponentFactory,
    private val serversComponentFactory: ServersComponentFactory,
    private val servicesComponentFactory: ServicesComponentFactory,
    context: ComponentContext,
    private val rootNavigation: StackNavigation<RootNavigationConfig>
) : ComposeComponent, ComponentContext by context {
    private val navigation = StackNavigation<Config>()

    private val internalStack: Value<ChildStack<Config, ComposeComponent>> =
        childStack<Config, ComposeComponent>(
            source = navigation,
            initialConfiguration = Config.Servers,
            handleBackButton = true,
            childFactory = ::child
        )

    val stack: Value<ChildStack<*, ComposeComponent>> = internalStack
    val selectedDrawerElement = internalStack.map { it.active.configuration.drawerElement }

    fun onSelectDrawerElement(drawerElement: DrawerElement) {
        when (drawerElement) {
            DrawerElement.Entities -> navigation.navigate { listOf(Config.Entities) }
            DrawerElement.Services -> navigation.navigate { listOf(Config.Services) }
            DrawerElement.Servers -> navigation.navigate { listOf(Config.Servers) }
        }
    }

    private fun child(config: Config, componentContext: ComponentContext): ComposeComponent {
        return when (config) {
            is Config.Entities -> entitiesComponent(componentContext)
            is Config.Services -> servicesComponent(componentContext)
            is Config.Servers -> serversComponent(componentContext)
        }
    }

    private fun entitiesComponent(componentContext: ComponentContext): ComposeComponent {
        return entitiesComponentFactory.create(componentContext)
    }

    private fun servicesComponent(componentContext: ComponentContext): ComposeComponent {
        return servicesComponentFactory.create(componentContext)
    }

    private fun serversComponent(context: ComponentContext): ComposeComponent {
        return serversComponentFactory.create(
            openAddServerScreen = { rootNavigation.push(RootNavigationConfig.EditServer(null)) },
            openEditServerScreen = { serverId -> rootNavigation.push(RootNavigationConfig.EditServer(serverId)) },
            context = context,
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
