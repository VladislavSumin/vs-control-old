package ru.vs.control.ui.root

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ru.vs.core.uikit.local_configuration.LocalConfigurationHolder

@Composable
fun RootContent(component: RootComponent) {
    LocalConfigurationHolder {
        MaterialTheme {
            component.navigationComponent.Render()
        }
    }
}
