package ru.vs.control.ui.root

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun RootContent(component: RootComponent) {
    MaterialTheme {
        component.navigationComponent.Render()
    }
}
