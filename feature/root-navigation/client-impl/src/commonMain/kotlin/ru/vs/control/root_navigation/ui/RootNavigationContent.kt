package ru.vs.control.root_navigation.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@Composable
internal fun RootNavigationContent(component: RootNavigationComponent) {
    Children(
        stack = component.stack,
    ) {
        it.instance.Render()
    }
}
