package ru.vs.control.entities.dto

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.vs.control.entities.domain.EntityPrimaryState
import ru.vs.control.entities.domain.StubEntityPrimaryState

internal interface EntityPrimaryStateSerializerModuleFactory {
    fun create(): SerializersModule
}

internal class EntityPrimaryStateSerializerModuleFactoryImpl : EntityPrimaryStateSerializerModuleFactory {
    override fun create(): SerializersModule {
        return SerializersModule {
            polymorphic(EntityPrimaryState::class) {
                subclass(StubEntityPrimaryState::class)
            }
        }
    }
}
