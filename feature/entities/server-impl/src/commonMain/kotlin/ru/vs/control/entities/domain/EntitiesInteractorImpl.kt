package ru.vs.control.entities.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import ru.vs.control.entities.repository.EntitiesRegistry

internal class EntitiesInteractorImpl(
    private val entitiesRegistry: EntitiesRegistry,
) : EntitiesInteractor {
    override fun observeEntities(): Flow<Entities<*>> {
        return entitiesRegistry.observeEntities()
    }

    override suspend fun <T : EntityState> holdEntity(
        id: EntityId,
        primaryState: T,
        properties: EntityProperties,
        block: suspend (update: suspend ((entity: Entity<T>) -> T) -> Unit) -> Unit,
    ) {
        val entity = EntityImpl(
            id = id,
            primaryState = primaryState,
            properties = properties,
        )
        entitiesRegistry.holdEntity(entity, block)
    }

    override suspend fun <T : EntityState> holdConstantEntity(
        id: EntityId,
        primaryState: T,
        properties: EntityProperties
    ): Nothing {
        val entity = EntityImpl(
            id = id,
            primaryState = primaryState,
            properties = properties,
        )
        entitiesRegistry.holdEntity(entity) { delay(Long.MAX_VALUE) }
        error("unreachable code")
    }
}
