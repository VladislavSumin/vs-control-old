package ru.vs.control.entities.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import ru.vs.control.entities.repository.EntitiesRegistry

internal class EntitiesInteractorImpl(
    private val entitiesRegistry: EntitiesRegistry,
) : EntitiesInteractor {
    override fun observeEntities(): Flow<Map<EntityId, Entity<*>>> {
        return entitiesRegistry.observeEntities()
    }

    override suspend fun <T : EntityState> holdEntity(
        initialValue: Entity<T>,
        block: suspend (update: suspend ((entity: Entity<T>) -> Entity<T>) -> Unit) -> Unit
    ) {
        entitiesRegistry.holdEntity(initialValue, block)
    }

    override suspend fun <T : EntityState> holdConstantEntity(value: Entity<T>): Nothing {
        entitiesRegistry.holdEntity(value) { delay(Long.MAX_VALUE) }
        error("unreachable code")
    }
}
