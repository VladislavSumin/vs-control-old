package ru.vs.control.entities.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.vs.control.entities.domain.Entity
import ru.vs.control.id.CompositeId

internal interface EntitiesRegistry {
    fun observeEntities(): Flow<Map<CompositeId, Entity>>

    suspend fun setEntity(entity: Entity)
}

internal class EntitiesRegistryImpl : EntitiesRegistry {
    private val entities = MutableStateFlow(mapOf<CompositeId, Entity>())

    init {
        GlobalScope.launch {
            setEntity(Entity(CompositeId("service1#entity1")))
            setEntity(Entity(CompositeId("service1#entity2")))
            setEntity(Entity(CompositeId("service1#entity3")))
            setEntity(Entity(CompositeId("service1#entity4")))
            setEntity(Entity(CompositeId("service1#entity5")))
            setEntity(Entity(CompositeId("service1#entity6")))
            setEntity(Entity(CompositeId("service1#entity7")))
            setEntity(Entity(CompositeId("service1#entity8")))
        }
    }

    override fun observeEntities(): Flow<Map<CompositeId, Entity>> {
        return entities
    }

    override suspend fun setEntity(entity: Entity) {
        entities.update {
            val newValue = it.toMutableMap()
            newValue[entity.id] = entity
            newValue
        }
    }
}
