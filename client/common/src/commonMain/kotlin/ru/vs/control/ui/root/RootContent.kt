package ru.vs.control.ui.root

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.uikit.local_configuration.LocalConfigurationHolder

@Composable
fun RootContent(component: RootComponent) {
    LocalConfigurationHolder {
        MaterialTheme {
            component.navigationComponent.Render(Modifier)
        }
    }
}
