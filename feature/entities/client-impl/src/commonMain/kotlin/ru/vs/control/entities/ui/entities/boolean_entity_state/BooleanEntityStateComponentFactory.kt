package ru.vs.control.entities.ui.entities.boolean_entity_state

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.base_entity_states.BooleanEntityState
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponent
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactory
import kotlin.reflect.KClass

internal class BooleanEntityStateComponentFactory : EntityStateComponentFactory<BooleanEntityState> {
    override val entityStateType: KClass<BooleanEntityState> = BooleanEntityState::class

    override fun create(
        state: StateFlow<Entity<BooleanEntityState>>,
        context: ComponentContext
    ): EntityStateComponent<BooleanEntityState> {
        return BooleanEntityStateComponent(state, context)
    }
}
