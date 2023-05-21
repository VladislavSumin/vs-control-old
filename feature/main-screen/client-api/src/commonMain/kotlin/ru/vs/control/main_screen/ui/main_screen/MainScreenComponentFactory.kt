package ru.vs.control.main_screen.ui.main_screen

import com.arkivanov.decompose.router.stack.StackNavigation
import ru.vs.control.root_navigation.ui.RootNavigationConfig
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

interface MainScreenComponentFactory {
    fun create(
        context: DiComponentContext,
        rootNavigation: StackNavigation<RootNavigationConfig>,
    ): ComposeComponent
}
