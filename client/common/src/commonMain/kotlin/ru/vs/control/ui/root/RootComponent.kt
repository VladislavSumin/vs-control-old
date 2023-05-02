package ru.vs.control.ui.root

import ru.vs.core.decompose.DiComponentContext
import ru.vs.core.root_navigation.ui.RootNavigationComponent

class RootComponent(
    diComponentContext: DiComponentContext
) : DiComponentContext by diComponentContext {
    internal val navigationComponent = RootNavigationComponent(this)
}
