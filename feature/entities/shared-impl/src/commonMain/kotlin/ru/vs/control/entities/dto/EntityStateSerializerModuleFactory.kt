package ru.vs.control.entities.dto

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.vs.control.entities.domain.EntityState
import ru.vs.control.entities.domain.ExternalEntitySerializer
import ru.vs.control.entities.domain.base_entity_states.BooleanEntityState

internal interface EntityStateSerializerModuleFactory {
    fun create(): SerializersModule
}

internal class EntityStateSerializerModuleFactoryImpl(
    private val externalEntitiesSerializers: Set<ExternalEntitySerializer<EntityState>>,
) : EntityStateSerializerModuleFactory {
    override fun create(): SerializersModule {
        return SerializersModule {
            polymorphic(EntityState::class) {
                subclass(BooleanEntityState::class)
                externalEntitiesSerializers.forEach {
                    subclass(it.kClass, it.kSerializer)
                }
            }
        }
    }
}
