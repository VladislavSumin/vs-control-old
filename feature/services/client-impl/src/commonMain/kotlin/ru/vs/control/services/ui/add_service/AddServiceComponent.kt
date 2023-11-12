package ru.vs.control.services.ui.add_service

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(AddServiceComponentFactory::class)
internal class AddServiceComponent(
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {
    @Composable
    override fun Render(modifier: Modifier) {

    }
}
