package ru.vs.control.ui.root

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import ru.vs.control.servers.ui.servers.ServersContent

@Composable
fun RootContent(component: RootComponent) {
    MaterialTheme {
        Children(
            stack = component.stack
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.ServersScreen -> ServersContent(child.serversComponent)
            }
        }
    }
}
