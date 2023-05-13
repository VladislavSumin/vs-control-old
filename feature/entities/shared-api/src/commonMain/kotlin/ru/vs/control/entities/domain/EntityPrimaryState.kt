package ru.vs.control.entities.domain

import kotlinx.serialization.Serializable

/**
 * Abstract because interface not support [Serializable] annotation
 */
@Suppress("UnnecessaryAbstractClass")
@Serializable
abstract class EntityPrimaryState

/**
 * Just for test, it will be removed later
 */
@Serializable
object StubEntityPrimaryState : EntityPrimaryState()
