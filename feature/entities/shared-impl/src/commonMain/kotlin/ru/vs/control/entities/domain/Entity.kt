package ru.vs.control.entities.domain

import ru.vs.control.entities.dto.EntityDto

data class Entity(val id: String)

fun EntityDto.toEntity() = Entity(id)
fun Entity.toDto() = EntityDto(id)

fun List<EntityDto>.toEntity() = map { it.toEntity() }
fun List<Entity>.toDto() = map { it.toDto() }
