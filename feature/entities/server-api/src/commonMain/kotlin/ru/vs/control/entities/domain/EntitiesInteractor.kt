package ru.vs.control.entities.domain

import kotlinx.coroutines.flow.Flow
import ru.vs.control.id.CompositeId

interface EntitiesInteractor {
    fun observeEntities(): Flow<Map<CompositeId, Entity>>
}
