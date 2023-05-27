package ru.vs.control.entities.ui.entities.boolean_entity_state

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.base_entity_states.BooleanEntityState
import ru.vs.control.entities.ui.entities.entity_state.BaseEntityStateComponent

internal class BooleanEntityStateComponent(
    state: StateFlow<Entity<BooleanEntityState>>,
    context: ComponentContext,
) : BaseEntityStateComponent<BooleanEntityState>(state, context) {
    @Composable
    override fun Render() = BooleanEntityStateContent(this)
}
