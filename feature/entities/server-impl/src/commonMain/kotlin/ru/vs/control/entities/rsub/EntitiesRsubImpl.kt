package ru.vs.control.entities.rsub

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.entities.dto.EntityDto
import ru.vs.control.entities.dto.toDto

internal class EntitiesRsubImpl(
    private val entitiesInteractor: EntitiesInteractor,
) : EntitiesRsub {
    override fun observeEntities(): Flow<List<EntityDto>> {
        return entitiesInteractor.observeEntities().map { it.values.toDto() }
    }
}
