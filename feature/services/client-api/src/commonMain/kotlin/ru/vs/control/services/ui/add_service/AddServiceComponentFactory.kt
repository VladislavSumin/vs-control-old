package ru.vs.control.services.ui.add_service

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

interface AddServiceComponentFactory {
    fun create(context: ComponentContext): ComposeComponent
}