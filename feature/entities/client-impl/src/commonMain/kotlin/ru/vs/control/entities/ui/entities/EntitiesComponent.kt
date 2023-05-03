package ru.vs.control.entities.ui.entities

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

class EntitiesComponent(context: DiComponentContext) : ComposeComponent, DiComponentContext by context {
    @Composable
    override fun Render() {
        Text("Entities screen")
    }
}
