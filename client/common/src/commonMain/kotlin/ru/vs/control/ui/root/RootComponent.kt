package ru.vs.control.ui.root

import com.arkivanov.decompose.ComponentContext
import ru.vs.control.root_navigation.ui.RootNavigationComponentFactory

class RootComponent(
    rootNavigationComponentFactory: RootNavigationComponentFactory,
    diComponentContext: ComponentContext
) : ComponentContext by diComponentContext {
    internal val navigationComponent = rootNavigationComponentFactory.create(this)
}
