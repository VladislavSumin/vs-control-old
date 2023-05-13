package ru.vs.control.entities.repository

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import ru.vs.control.entities.domain.Entity
import ru.vs.control.id.CompositeId

internal interface EntitiesRegistry {
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

internal class EntitiesRegistryImpl : EntitiesRegistry {
    private val storage = EntitiesStorage()

    override fun observeEntities(): Flow<Map<CompositeId, Entity>> {
        return storage.entities
    }

    override suspend fun holdEntity(
        initialValue: Entity,
        block: suspend (update: suspend ((entity: Entity) -> Entity) -> Unit) -> Unit
    ) {
        val id = initialValue.id
        var currentEntity: Entity = initialValue

        storage.update { entities ->
            if (entities.containsKey(id)) error("Entity with ${initialValue.id} already exist")
            entities[id] = initialValue
        }

        try {
            block { updateFunction ->

                val newValue = updateFunction(currentEntity)

                check(newValue.id == id) {
                    "Illegal entity update. Trying update old state with $id to new state with ${newValue.id}"
                }

                storage.update { it[id] = newValue }
                currentEntity = newValue
            }
        } finally {
            withContext(NonCancellable) {
                storage.update { it.remove(id) }
            }
        }
    }

    /**
     * Given safely access to internal storage collection
     */
    private class EntitiesStorage {
        private val entitiesMut = MutableStateFlow(mapOf<CompositeId, Entity>())
        private val lock = Mutex()

        val entities: StateFlow<Map<CompositeId, Entity>> = entitiesMut

        suspend fun update(block: (entities: MutableMap<CompositeId, Entity>) -> Unit) {
            lock.withLock {
                val newData = entitiesMut.value.toMutableMap()
                block(newData)
                entitiesMut.value = newData
            }
        }
    }
}
