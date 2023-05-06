package ru.vs.control.entities.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.control.entities.repository.EntitiesRegistry

internal interface EntitiesInteractor {
    fun observeEntities(): Flow<List<Entity>>
}

internal class EntitiesInteractorImpl(
    private val entitiesRegistry: EntitiesRegistry,
) : EntitiesInteractor {
    override fun observeEntities(): Flow<List<Entity>> {
        return entitiesRegistry.observeEntities()
    }
}
