package ru.vs.control.entities.domain

/**
 * Primary class for containing entity state
 * @param id - entity id, must be unique
 * @param primaryState - entity primary state (type don't change on entities updates)
 * @param T - primary state type
 */
data class Entity<T : EntityState>(val id: EntityId, val primaryState: T)

typealias Entities<T> = Map<EntityId, Entity<T>>
