package ru.vs.control.entities.ui.entities.entity_state

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.EntityState

/**
 * Factory to create instances of [EntityStateComponent]
 * TODO add about register documentation
 */
interface EntityStateComponentFactory<T : EntityState> {
    /**
     * @param state - flow emits actual state for given [Entity]
     * @param context - child context to use as [ComponentContext] delegate when creating [EntityStateComponent]
     */
    fun create(state: StateFlow<Entity<T>>, context: ComponentContext): EntityStateComponent<T>
}
