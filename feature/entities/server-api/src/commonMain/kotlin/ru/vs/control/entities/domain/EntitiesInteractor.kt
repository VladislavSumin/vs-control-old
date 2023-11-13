package ru.vs.control.entities.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update

interface EntitiesInteractor {
    fun observeEntities(): Flow<Entities<*>>

    /**
     * Holds entity with given [id] while [block] is running. When exits from [block] remove entity from registry.
     *
     * [update] update function to safely update current entity state
     *
     * @param id entity id
     * @param primaryState entity primary state
     * @param properties entity properties
     * @param block block running at caller coroutine context
     */
    suspend fun <T : EntityState> holdEntity(
        id: EntityId,
        primaryState: T,
        properties: EntityProperties = EntityProperties(),
        block: suspend (
            update: suspend (
                (entity: Entity<T>) -> T
            ) -> Unit
        ) -> Unit
    )

    /**
     * Holds entity with given [id] while called coroutine is running.
     * When cancel called coroutine scope remove entity from registry
     *
     * @param id entity id
     * @param primaryState entity primary state
     * @param properties entity properties
     */
    suspend fun <T : EntityState> holdConstantEntity(
        id: EntityId,
        primaryState: T,
        properties: EntityProperties = EntityProperties(),
    ): Nothing
}
