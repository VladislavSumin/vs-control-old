package ru.vs.control.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.vs.control.servers.ui.servers.DefaultServersComponent
import ru.vs.control.servers.ui.servers.ServersComponent
import ru.vs.control.ui.root.RootComponent.Child

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class ServersScreen(val serversComponent: ServersComponent) : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val stack_: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Servers,
        handleBackButton = true,
        childFactory = ::child
    )

    override val stack: Value<ChildStack<*, Child>> = stack_

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Servers -> Child.ServersScreen(serversComponent(componentContext))
        }

    private fun serversComponent(componentContext: ComponentContext): DefaultServersComponent {
        return DefaultServersComponent(componentContext)
    }

    @Parcelize
    private sealed interface Config : Parcelable {
        object Servers : Config
    }
}