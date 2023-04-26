package ru.vs.control.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.control.servers.ui.servers.ServersComponent
import ru.vs.core.decompose.ComposeComponent

interface RootComponent {
    val stack: Value<ChildStack<*, ComposeComponent>>
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val internalStack: Value<ChildStack<Config, ComposeComponent>> = childStack(
        source = navigation,
        initialConfiguration = Config.Servers,
        handleBackButton = true,
        childFactory = ::child
    )

    override val stack: Value<ChildStack<*, ComposeComponent>> = internalStack

    private fun child(config: Config, componentContext: ComponentContext): ComposeComponent =
        when (config) {
            is Config.Servers -> serversComponent(componentContext)
        }

    private fun serversComponent(componentContext: ComponentContext): ServersComponent {
        return ServersComponent(componentContext)
    }

    @Parcelize
    private sealed interface Config : Parcelable {
        object Servers : Config
    }
}
