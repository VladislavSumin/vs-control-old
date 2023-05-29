package ru.vs.control.entities.dto

import ru.vs.control.entities.domain.Entity

fun EntityDto.toEntity() = Entity(id, primaryState)
fun Collection<EntityDto>.toEntity() = map { it.toEntity() }
