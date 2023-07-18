package ru.vs.control.services.ui.services

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

internal class ServicesComponent(context: ComponentContext) : ComposeComponent, ComponentContext by context {

    @Composable
    override fun Render() = ServicesContent(this)
}
