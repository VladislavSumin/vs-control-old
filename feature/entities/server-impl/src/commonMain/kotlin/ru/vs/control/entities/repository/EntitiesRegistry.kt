package ru.vs.control.entities.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.vs.control.entities.domain.Entity
import ru.vs.control.id.Id

internal interface EntitiesRegistry {
    fun observeEntities(): Flow<List<Entity>>
}

internal class EntitiesRegistryImpl : EntitiesRegistry {
    override fun observeEntities(): Flow<List<Entity>> {
        return flowOf(
            listOf(
                Entity(Id("entity1")),
                Entity(Id("entity2")),
                Entity(Id("entity3")),
                Entity(Id("entity4")),
                Entity(Id("entity5")),
                Entity(Id("entity6")),
                Entity(Id("entity7")),
            )
        )
    }
}
