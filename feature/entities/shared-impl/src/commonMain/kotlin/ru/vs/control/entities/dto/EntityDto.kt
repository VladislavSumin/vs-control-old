package ru.vs.control.entities.dto

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.BaseEntity
import ru.vs.control.entities.domain.EntityId
import ru.vs.control.entities.domain.EntityState

@Serializable
data class EntityDto(val id: EntityId, val primaryState: EntityState)

fun BaseEntity<*>.toDto() = EntityDto(id, primaryState)
fun Collection<BaseEntity<*>>.toDto() = map { it.toDto() }
