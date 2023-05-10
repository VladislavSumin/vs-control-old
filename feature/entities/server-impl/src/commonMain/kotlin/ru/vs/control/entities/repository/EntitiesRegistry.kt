package ru.vs.control.entities.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.vs.control.entities.domain.Entity
import ru.vs.control.id.CompositeId

internal interface EntitiesRegistry {
    fun observeEntities(): Flow<List<Entity>>
}

internal class EntitiesRegistryImpl : EntitiesRegistry {
    override fun observeEntities(): Flow<List<Entity>> {
        return flowOf(
            listOf(
                Entity(CompositeId("service1#entity1")),
                Entity(CompositeId("service1#entity2")),
                Entity(CompositeId("service1#entity3")),
                Entity(CompositeId("service1#entity4")),
                Entity(CompositeId("service1#entity5")),
                Entity(CompositeId("service1#entity6")),
                Entity(CompositeId("service1#entity7")),
                Entity(CompositeId("service1#entity8")),
            )
        )
    }
}
