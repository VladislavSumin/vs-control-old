package ru.vs.control.services.ui.services

import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

internal class ServicesComponentFactoryImpl : ServicesComponentFactory {
    override fun create(context: DiComponentContext): ComposeComponent {
        return ServicesComponent(context)
    }
}
