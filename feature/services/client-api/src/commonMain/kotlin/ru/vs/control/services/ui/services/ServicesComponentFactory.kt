package ru.vs.control.services.ui.services

import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

interface ServicesComponentFactory {
    fun create(context: DiComponentContext): ComposeComponent
}
