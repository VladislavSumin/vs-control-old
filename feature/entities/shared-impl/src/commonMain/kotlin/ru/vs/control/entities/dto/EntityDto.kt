package ru.vs.control.entities.dto

import kotlinx.serialization.Serializable
import ru.vs.control.id.CompositeId

@Serializable
data class EntityDto(val id: CompositeId)
