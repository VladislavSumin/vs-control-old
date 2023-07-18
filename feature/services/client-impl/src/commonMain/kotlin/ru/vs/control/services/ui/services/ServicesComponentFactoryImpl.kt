package ru.vs.control.services.ui.services

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

internal class ServicesComponentFactoryImpl : ServicesComponentFactory {
    override fun create(context: ComponentContext): ComposeComponent {
        return ServicesComponent(context)
    }
}
