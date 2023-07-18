package ru.vs.control.root_navigation.ui

import com.arkivanov.decompose.ComponentContext
import ru.vs.core.decompose.ComposeComponent

interface RootNavigationComponentFactory {
    fun create(context: ComponentContext): ComposeComponent
}
