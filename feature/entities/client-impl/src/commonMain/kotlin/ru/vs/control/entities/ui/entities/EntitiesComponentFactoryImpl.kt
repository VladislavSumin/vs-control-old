package ru.vs.control.entities.ui.entities

import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

internal class EntitiesComponentFactoryImpl:EntitiesComponentFactory {
    override fun create(componentContext: DiComponentContext): ComposeComponent {
        return EntitiesComponent(componentContext)
    }
}