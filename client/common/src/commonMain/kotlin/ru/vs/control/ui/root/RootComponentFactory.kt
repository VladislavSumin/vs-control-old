package ru.vs.control.ui.root

import com.arkivanov.decompose.ComponentContext
import ru.vs.control.root_navigation.ui.RootNavigationComponentFactory

interface RootComponentFactory {
    fun create(componentContext: ComponentContext): RootComponent
}

class RootComponentFactoryImpl(
    private val rootNavigationComponentFactory: RootNavigationComponentFactory,
) : RootComponentFactory {
    override fun create(componentContext: ComponentContext): RootComponent {
        return RootComponent(rootNavigationComponentFactory, componentContext)
    }
}
