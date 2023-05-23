package ru.vs.control.entities.domain

import kotlinx.serialization.Serializable

/**
 * Primary entity state, see [Entity.primaryState]
 * Abstract because interface not support [Serializable] annotation
 */
@Suppress("UnnecessaryAbstractClass")
@Serializable
abstract class EntityState
