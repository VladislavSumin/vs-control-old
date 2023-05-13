package ru.vs.control.entities.domain

import kotlinx.serialization.Serializable

/**
 * Abstract because interface not support [Serializable] annotation
 */
@Suppress("UnnecessaryAbstractClass")
@Serializable
abstract class EntityState
