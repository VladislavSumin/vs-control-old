package ru.vs.control.ui.root

import com.arkivanov.decompose.ComponentContext

interface RootComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext