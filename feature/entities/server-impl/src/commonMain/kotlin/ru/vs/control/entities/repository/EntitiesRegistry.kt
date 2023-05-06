package ru.vs.control.entities.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.vs.control.entities.domain.Entity

internal interface EntitiesRegistry {
    fun observeEntities(): Flow<List<Entity>>
}

internal class EntitiesRegistryImpl : EntitiesRegistry {
    override fun observeEntities(): Flow<List<Entity>> {
        return flowOf(
            listOf(
                Entity("entity1"),
                Entity("entity2"),
                Entity("entity3"),
                Entity("entity4"),
                Entity("entity5"),
                Entity("entity6"),
                Entity("entity7"),
            )
        )
    }
}
