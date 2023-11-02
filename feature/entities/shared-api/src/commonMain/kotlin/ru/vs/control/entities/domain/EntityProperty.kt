package ru.vs.control.entities.domain

import kotlinx.serialization.Serializable

/**
 * Additional entity property, see [BaseEntity.primaryState].//TODO
 *
 * Implementation of this interface must be [Serializable] and registered with special way,
 * see [ExternalEntityStateSerializer].//TODO
 */
interface EntityProperty
