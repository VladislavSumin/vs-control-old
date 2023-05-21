package ru.vs.control.entities.ui.entities

import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

interface EntitiesComponentFactory {
    fun create(componentContext: DiComponentContext): ComposeComponent
}
