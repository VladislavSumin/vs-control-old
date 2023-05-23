package ru.vs.control.entities.domain

import ru.vs.control.id.CompositeId

/**
 * Primary class for containing entity state
 * @param id - composite entity id, must be unique
 * @param primaryState - entity primary state (type don't change on entities updates)
 * @param T - primary state type
 */
data class Entity<T : EntityState>(val id: CompositeId, val primaryState: T)
