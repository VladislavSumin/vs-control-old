package ru.vs.control.entities.ui.entities

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

internal class EntitiesComponentFactoryImpl(
    private val entitiesStoreFactory: EntitiesStoreFactory,
) : EntitiesComponentFactory {
    override fun create(componentContext: ComponentContext): ComposeComponent {
        return EntitiesComponent(entitiesStoreFactory, componentContext)
    }
}
