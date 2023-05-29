package ru.vs.control.entities.ui.entities

import com.arkivanov.decompose.ComponentContext
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactoryRegistry
import ru.vs.core.decompose.ComposeComponent

internal class EntitiesComponentFactoryImpl(
    private val entityStateComponentFactoryRegistry: EntityStateComponentFactoryRegistry,
    private val entitiesViewModelFactory: EntitiesViewModelFactory,
) : EntitiesComponentFactory {
    override fun create(componentContext: ComponentContext): ComposeComponent {
        return EntitiesComponent(
            entityStateComponentFactoryRegistry,
            entitiesViewModelFactory,
            componentContext
        )
    }
}
