package ru.vs.control.entities.domain

/**
 * Primary class for server entity
 * @param id - entity id, must be unique
 * @param primaryState - entity primary state (type don't change on entities updates)
 * @param T - primary state type
 */
data class Entity<T : EntityState>(override val id: EntityId, override val primaryState: T) : BaseEntity<T>

typealias Entities<T> = Map<EntityId, Entity<T>>