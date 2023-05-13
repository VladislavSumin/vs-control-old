package ru.vs.control.entities.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import ru.vs.control.id.CompositeId

interface EntitiesInteractor {
    fun observeEntities(): Flow<Map<CompositeId, Entity>>

    /**
     * Holds entity with given [Entity.id] while [block] is running. When exits from [block] remove entity from registry
     *
     * [update] - update function to safely update current entity
     * Attention! Update function may update only one entity (id must be equal [initialValue] id)
     *
     * @param initialValue - initial entity state
     * @param block - block running at caller coroutine context
     */
    suspend fun holdEntity(
        initialValue: Entity,
        block: suspend (
            update: suspend (
                (entity: Entity) -> Entity
            ) -> Unit
        ) -> Unit
    )
}
