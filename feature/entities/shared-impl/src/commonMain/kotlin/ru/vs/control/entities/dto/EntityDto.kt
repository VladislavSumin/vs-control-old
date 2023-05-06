package ru.vs.control.entities.dto

import kotlinx.serialization.Serializable
import ru.vs.control.id.Id

@Serializable
data class EntityDto(val id: Id)
