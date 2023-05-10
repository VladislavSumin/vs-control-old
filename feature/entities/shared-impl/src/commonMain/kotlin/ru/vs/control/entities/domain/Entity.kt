package ru.vs.control.entities.domain

import ru.vs.control.entities.dto.EntityDto
import ru.vs.control.id.CompositeId

data class Entity(val id: CompositeId)

fun EntityDto.toEntity() = Entity(id)
fun Entity.toDto() = EntityDto(id)

fun List<EntityDto>.toEntity() = map { it.toEntity() }
fun List<Entity>.toDto() = map { it.toDto() }
