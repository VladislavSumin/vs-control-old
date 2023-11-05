package ru.vs.control.root_navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import ru.vs.core.navigation.WithSimpleNavigation

@Composable
internal fun RootNavigationContent(component: RootNavigationComponent) {
    WithSimpleNavigation(component::navigateBack) {
        Children(
            stack = component.stack,
        ) {
            it.instance.Render(Modifier)
        }
    }
}
