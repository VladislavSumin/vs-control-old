package ru.vs.control.root_navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@Composable
internal fun RootNavigationContent(component: RootNavigationComponent) {
    Children(
        stack = component.stack,
    ) {
        it.instance.Render(Modifier)
    }
}
