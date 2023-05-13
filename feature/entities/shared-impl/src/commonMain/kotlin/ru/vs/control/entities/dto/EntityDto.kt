package ru.vs.control.entities.dto

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.EntityState
import ru.vs.control.id.CompositeId

@Serializable
data class EntityDto(val id: CompositeId, val primaryState: EntityState)

fun EntityDto.toEntity() = Entity(id, primaryState)
fun Entity.toDto() = EntityDto(id, primaryState)

fun Collection<EntityDto>.toEntity() = map { it.toEntity() }
fun Collection<Entity>.toDto() = map { it.toDto() }
