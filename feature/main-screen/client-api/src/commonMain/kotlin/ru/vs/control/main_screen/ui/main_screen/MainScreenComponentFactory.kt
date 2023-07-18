package ru.vs.control.main_screen.ui.main_screen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import ru.vs.control.root_navigation.ui.RootNavigationConfig
import ru.vs.core.decompose.ComposeComponent

interface MainScreenComponentFactory {
    fun create(
        context: ComponentContext,
        rootNavigation: StackNavigation<RootNavigationConfig>,
    ): ComposeComponent
}
