package ru.vs.control.services.ui.services

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

interface ServicesComponentFactory {
    fun create(context: ComponentContext): ComposeComponent
}
