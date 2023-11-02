package ru.vs.control.entities.domain

import kotlin.reflect.KClass

/**
 * Collection of [EntityProperty].
 */
class EntityProperties private constructor(
    private val internalMap: Map<KClass<out EntityProperty>, EntityProperty>
) {
    val raw: Collection<EntityProperty> get() = internalMap.values

    /**
     * Allow any type of collections, but remember that property in collection must be unique by type.
     */
    constructor(properties: Collection<EntityProperty>) : this(
        internalMap = properties
            .associateBy { it::class }
            .also { check(it.size == properties.size) { "properties must be unique by type" } }
    )

    constructor() : this(emptyMap())

    /**
     * Getting property by it type or null.
     */
    operator fun <T : EntityProperty> get(type: KClass<T>): T? {
        val value = internalMap[type] ?: return null

        // Cast always must be success, type compliance checking on constructor
        @Suppress("UNCHECKED_CAST")
        return value as T
    }
}