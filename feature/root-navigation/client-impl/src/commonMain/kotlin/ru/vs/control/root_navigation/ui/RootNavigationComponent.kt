package ru.vs.control.root_navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import ru.vs.control.main_screen.ui.main_screen.MainScreenComponentFactory
import ru.vs.control.servers.ui.edit_server.EditServerComponentFactory
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(RootNavigationComponentFactory::class)
internal class RootNavigationComponent(
    private val mainScreenComponentFactory: MainScreenComponentFactory,
    private val editServerComponentFactory: EditServerComponentFactory,
    context: ComponentContext
) : ComposeComponent, ComponentContext by context {
    private val navigation = StackNavigation<RootNavigationConfig>()

    private val internalStack: Value<ChildStack<RootNavigationConfig, ComposeComponent>> =
        childStack<RootNavigationConfig, ComposeComponent>(
            source = navigation,
            initialConfiguration = RootNavigationConfig.MainScreen,
            handleBackButton = true,
            childFactory = ::child
        )

    internal val stack: Value<ChildStack<*, ComposeComponent>> = internalStack

    private fun child(config: RootNavigationConfig, componentContext: ComponentContext): ComposeComponent {
        return when (config) {
            is RootNavigationConfig.MainScreen -> mainScreenComponent(componentContext)
            is RootNavigationConfig.EditServer -> editServerComponent(componentContext, config.serverId)
        }
    }

    private fun mainScreenComponent(diComponentContext: ComponentContext): ComposeComponent {
        return mainScreenComponentFactory.create(
            diComponentContext,
            navigation,
        )
    }

    private fun editServerComponent(diComponentContext: ComponentContext, serverId: Long?): ComposeComponent {
        return editServerComponentFactory.create(
            diComponentContext,
            serverId,
            closeScreen = { navigation.pop() }
        )
    }

    @Composable
    override fun Render(modifier: Modifier) = RootNavigationContent(this)
}
