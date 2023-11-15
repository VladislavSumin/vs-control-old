package ru.vs.control.ui.root

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RootContent(component: RootComponent) {
    MaterialTheme {
        component.navigationComponent.Render(Modifier)
    }
}
