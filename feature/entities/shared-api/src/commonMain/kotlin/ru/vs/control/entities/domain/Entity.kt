package ru.vs.control.entities.domain

import ru.vs.control.id.CompositeId

data class Entity(val id: CompositeId, val primaryState: EntityState)
