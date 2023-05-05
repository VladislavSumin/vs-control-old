package ru.vs.control.entities.rsub

import kotlinx.coroutines.flow.Flow
import ru.vs.control.entities.dto.EntityDto
import ru.vs.rsub.RSubInterface

@RSubInterface
interface EntitiesRsub {
    fun observeEntities(): Flow<EntityDto>
}
