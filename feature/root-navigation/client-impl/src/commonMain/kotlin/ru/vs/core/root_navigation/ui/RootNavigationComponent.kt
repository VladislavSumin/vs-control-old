package ru.vs.core.root_navigation.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import ru.vs.control.main_screen.ui.main_screen.MainScreenComponent
import ru.vs.control.root_navigation.ui.RootNavigationConfig
import ru.vs.control.servers.ui.edit_server.EditServerComponent
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

class RootNavigationComponent(context: DiComponentContext) : ComposeComponent, DiComponentContext by context {
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
        val diComponentContext = DiComponentContext(componentContext, di)
        return when (config) {
            is RootNavigationConfig.MainScreen -> mainScreenComponent(diComponentContext)
            is RootNavigationConfig.EditServer -> editServerComponent(diComponentContext, config.serverId)
        }
    }

    private fun mainScreenComponent(diComponentContext: DiComponentContext): MainScreenComponent {
        return MainScreenComponent(
            diComponentContext,
            navigation,
        )
    }

    private fun editServerComponent(diComponentContext: DiComponentContext, serverId: Long?): EditServerComponent {
        return EditServerComponent(
            diComponentContext,
            serverId,
            closeScreen = { navigation.pop() }
        )
    }

    @Composable
    override fun Render() = RootNavigationContent(this)
}
