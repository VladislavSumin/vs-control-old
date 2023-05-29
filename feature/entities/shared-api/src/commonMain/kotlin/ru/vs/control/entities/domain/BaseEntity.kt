package ru.vs.control.entities.domain

/**
 * Base interface for any Entity, contains shared entity part for client and server,
 * and extends by Entity interface on client and server implementations
 *
 * @param T - primary state type
 */
interface BaseEntity<T : EntityState> {
    /**
     *  entity id, must be unique
     */
    val id: EntityId

    /**
     * entity primary state (type don't change on entities updates)
     */
    val primaryState: T
}
