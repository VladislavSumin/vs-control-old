package ru.vs.control.entities.ui.entities.entity_state

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.EntityState
import ru.vs.control.entities.domain.base_entity_states.BooleanEntityState
import ru.vs.control.entities.ui.entities.boolean_entity_state.BooleanEntityStateComponentFactory
import ru.vs.control.entities.ui.entities.unknown_entity_state.UnknownEntityStateComponent
import kotlin.reflect.KClass

internal interface EntityStateComponentFactoryRegistry {
    /**
     * Creates instance of implementation [EntityStateComponent] for given [Entity.primaryState]
     * If [Entity.primaryState] is unknown create instance of [UnknownEntityStateComponent]
     */
    fun create(
        state: StateFlow<Entity<out EntityState>>,
        context: ComponentContext,
    ): EntityStateComponent<*>
}

internal class EntityStateComponentFactoryRegistryImpl : EntityStateComponentFactoryRegistry {
    private val factories: Map<KClass<BooleanEntityState>, EntityStateComponentFactory<*>> = mapOf(
        BooleanEntityState::class to BooleanEntityStateComponentFactory(),
    )

    override fun create(state: StateFlow<Entity<out EntityState>>, context: ComponentContext): EntityStateComponent<*> {
        val factory = factories[state.value.primaryState::class]
        // We check types manually when registering factory, no additional check needed
        @Suppress("UNCHECKED_CAST")
        return factory?.create(state as StateFlow<Nothing>, context) ?: UnknownEntityStateComponent(state, context)
    }
}
