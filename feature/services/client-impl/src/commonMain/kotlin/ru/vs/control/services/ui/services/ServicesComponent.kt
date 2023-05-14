package ru.vs.control.services.ui.services

import androidx.compose.runtime.Composable
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

class ServicesComponent(context: DiComponentContext) : ComposeComponent, DiComponentContext by context {

    @Composable
    override fun Render() = ServicesContent(this)
}
