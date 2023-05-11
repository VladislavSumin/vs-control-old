package ru.vs.control.entities.dto

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.Entity
import ru.vs.control.id.CompositeId

@Serializable
data class EntityDto(val id: CompositeId)

fun EntityDto.toEntity() = Entity(id)
fun Entity.toDto() = EntityDto(id)

fun Collection<EntityDto>.toEntity() = map { it.toEntity() }
fun Collection<Entity>.toDto() = map { it.toDto() }
