package ru.vs.control.entities.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update

interface EntitiesInteractor {
    fun observeEntities(): Flow<Map<EntityId, Entity<*>>>

    /**
     * Holds entity with given [Entity.id] while [block] is running. When exits from [block] remove entity from registry
     *
     * [update] - update function to safely update current entity
     * Attention! Update function may update only one entity (id must be equal [initialValue] id)
     *
     * @param initialValue - initial entity state
     * @param block - block running at caller coroutine context
     */
    suspend fun <T : EntityState> holdEntity(
        initialValue: Entity<T>,
        block: suspend (
            update: suspend (
                (entity: Entity<T>) -> Entity<T>
            ) -> Unit
        ) -> Unit
    )

    /**
     * Holds entity with given [Entity.id] while called coroutine is running.
     * When cancel called coroutine scope remove entity from registry
     */
    suspend fun <T : EntityState> holdConstantEntity(
        value: Entity<T>,
    ): Nothing
}
