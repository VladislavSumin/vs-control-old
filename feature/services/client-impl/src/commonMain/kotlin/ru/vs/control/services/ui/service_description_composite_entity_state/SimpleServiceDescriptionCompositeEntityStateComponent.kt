package ru.vs.control.services.ui.service_description_composite_entity_state

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.factory_generator.GenerateEntityStateComponentFactory
import ru.vs.control.entities.ui.entities.entity_state.BaseEntityStateComponent
import ru.vs.control.services.entity_states.SimpleServiceDescriptionCompositeEntityState

@GenerateEntityStateComponentFactory
internal class SimpleServiceDescriptionCompositeEntityStateComponent(
    state: StateFlow<Entity<SimpleServiceDescriptionCompositeEntityState>>,
    componentContext: ComponentContext,
) : BaseEntityStateComponent<SimpleServiceDescriptionCompositeEntityState>(state, componentContext) {

    @Composable
    override fun Render(modifier: Modifier) = ServiceDescriptionCompositeEntityStateContent(this, modifier)
}
