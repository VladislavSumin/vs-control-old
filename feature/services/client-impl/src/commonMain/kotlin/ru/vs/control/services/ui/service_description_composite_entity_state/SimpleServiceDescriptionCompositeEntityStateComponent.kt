package ru.vs.control.services.ui.service_description_composite_entity_state

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.ui.entities.entity_state.BaseEntityStateComponent
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponent
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactory
import ru.vs.control.services.entity_states.SimpleServiceDescriptionCompositeEntityState
import kotlin.reflect.KClass

internal class SimpleServiceDescriptionCompositeEntityStateComponent(
    state: StateFlow<Entity<SimpleServiceDescriptionCompositeEntityState>>,
    componentContext: ComponentContext,
) : BaseEntityStateComponent<SimpleServiceDescriptionCompositeEntityState>(state, componentContext) {

    @Composable
    override fun Render(modifier: Modifier) = ServiceDescriptionCompositeEntityStateContent(this, modifier)
}

internal class SimpleServiceDescriptionCompositeEntityStateComponentFactory :
    EntityStateComponentFactory<SimpleServiceDescriptionCompositeEntityState> {

    override val entityStateType: KClass<SimpleServiceDescriptionCompositeEntityState> =
        SimpleServiceDescriptionCompositeEntityState::class

    override fun create(
        state: StateFlow<Entity<SimpleServiceDescriptionCompositeEntityState>>,
        context: ComponentContext
    ): EntityStateComponent<SimpleServiceDescriptionCompositeEntityState> {
        return SimpleServiceDescriptionCompositeEntityStateComponent(
            state,
            context,
        )
    }
}
