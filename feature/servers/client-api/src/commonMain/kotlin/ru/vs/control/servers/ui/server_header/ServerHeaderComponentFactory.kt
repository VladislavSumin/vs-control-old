package ru.vs.control.servers.ui.server_header

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

interface ServerHeaderComponentFactory {
    fun create(context: ComponentContext): ComposeComponent
}
