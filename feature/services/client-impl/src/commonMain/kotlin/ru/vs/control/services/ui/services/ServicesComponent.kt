package ru.vs.control.services.ui.services

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(ServicesComponentFactory::class)
internal class ServicesComponent(context: ComponentContext) : ComposeComponent, ComponentContext by context {

    @Composable
    override fun Render(modifier: Modifier) = ServicesContent(this)
}
