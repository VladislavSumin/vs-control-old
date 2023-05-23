package ru.vs.control.entities.domain

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * Used to pass external serializer for custom [EntityState]
 *
 * Bind our custom entities serializer into root kodein module:
 * ```
 * inBindSet<ExternalEntitySerializer<out EntityState>> {
 *     add { singleton { ExternalEntitySerializer<ExampleEntityState>() } }
 * }
 *```
 *
 * @param kClass - custom entity state class
 * @param kSerializer - serializer for [kClass] entity
 */
data class ExternalEntitySerializer<T : EntityState>(val kClass: KClass<T>, val kSerializer: KSerializer<T>) {
    companion object {
        inline operator fun <reified T : EntityState> invoke(): ExternalEntitySerializer<T> {
            return ExternalEntitySerializer(T::class, serializer())
        }
    }
}
