package ru.vs.control.ui.root

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@Composable
fun RootContent(component: RootComponent) {
    MaterialTheme {
        Children(
            stack = component.stack
        ) {
            it.instance.Render()
        }
    }
}
