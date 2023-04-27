package ru.vs.control.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.control.servers.ui.edit_server.EditServerComponent
import ru.vs.control.servers.ui.servers.ServersComponent
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

interface RootComponent {
    val stack: Value<ChildStack<*, ComposeComponent>>
}

class DefaultRootComponent(
    diComponentContext: DiComponentContext
) : RootComponent, DiComponentContext by diComponentContext {
    private val navigation = StackNavigation<Config>()

    private val internalStack: Value<ChildStack<Config, ComposeComponent>> = childStack(
        source = navigation,
        initialConfiguration = Config.Servers,
        handleBackButton = true,
        childFactory = ::child
    )

    override val stack: Value<ChildStack<*, ComposeComponent>> = internalStack

    private fun child(config: Config, componentContext: ComponentContext): ComposeComponent {
        val diComponentContext = DiComponentContext(componentContext, di)
        return when (config) {
            is Config.Servers -> serversComponent(diComponentContext)
            is Config.EditServer -> editServerComponent(diComponentContext, config.serverId)
        }
    }

    private fun editServerComponent(diComponentContext: DiComponentContext, serverId: Long?): EditServerComponent {
        return EditServerComponent(
            diComponentContext,
            serverId,
            closeScreen = { navigation.pop() }
        )
    }

    private fun serversComponent(componentContext: DiComponentContext): ServersComponent {
        return ServersComponent(
            componentContext,
            openAddServerScreen = { navigation.push(Config.EditServer(null)) },
        )
    }

    @Parcelize
    private sealed interface Config : Parcelable {
        object Servers : Config
        data class EditServer(val serverId: Long?) : Config
    }
}
