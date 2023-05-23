package ru.vs.control.entities.ui.entities.entity_state

import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.core.decompose.DiComponentContext

/**
 * Factory to create instances of [EntityStateComponent]
 * TODO add about register documentation
 */
interface EntityStateComponentFactory {
    /**
     * @param state - flow emits actual state for given [Entity]
     * @param context - child context to use as [DiComponentContext] delegate when creating [EntityStateComponent]
     */
    fun create(state: StateFlow<Entity>, context: DiComponentContext): EntityStateComponent
}
