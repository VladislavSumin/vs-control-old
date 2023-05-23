package ru.vs.control.entities.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import ru.vs.control.entities.repository.EntitiesRegistry
import ru.vs.control.id.CompositeId

internal class EntitiesInteractorImpl(
    private val entitiesRegistry: EntitiesRegistry,
) : EntitiesInteractor {
    override fun observeEntities(): Flow<Map<CompositeId, Entity>> {
        return entitiesRegistry.observeEntities()
    }

    override suspend fun holdEntity(
        initialValue: Entity,
        block: suspend (update: suspend ((entity: Entity) -> Entity) -> Unit) -> Unit
    ) {
        entitiesRegistry.holdEntity(initialValue, block)
    }

    override suspend fun holdConstantEntity(value: Entity): Nothing {
        entitiesRegistry.holdEntity(value) { delay(Long.MAX_VALUE) }
        error("unreachable code")
    }
}
