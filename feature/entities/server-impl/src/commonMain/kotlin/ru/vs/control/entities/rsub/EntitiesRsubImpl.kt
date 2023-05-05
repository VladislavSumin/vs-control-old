package ru.vs.control.entities.rsub

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.vs.control.entities.dto.EntityDto

internal class EntitiesRsubImpl : EntitiesRsub {
    override fun observeEntities(): Flow<List<EntityDto>> {
        return flowOf(
            listOf(
                EntityDto("entity1"),
                EntityDto("entity2"),
                EntityDto("entity3"),
                EntityDto("entity4"),
                EntityDto("entity5"),
                EntityDto("entity6"),
                EntityDto("entity7"),
            )
        )
    }
}
