package ru.vs.control.entities

import kotlinx.serialization.modules.SerializersModule
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.inBindSet
import org.kodein.di.singleton
import ru.vs.control.entities.dto.EntityStateSerializerModuleFactory
import ru.vs.control.entities.dto.EntityStateSerializerModuleFactoryImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureEntitiesShared() = DI.Module("feature-entities-shared") {
    bindSingleton<EntityStateSerializerModuleFactory> { EntityStateSerializerModuleFactoryImpl() }

    // Register EntityPrimaryState serializer module in default json parser instance
    inBindSet<SerializersModule> {
        add { singleton { i<EntityStateSerializerModuleFactory>().create() } }
    }
}
