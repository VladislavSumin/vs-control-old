package ru.vs.control.servers.ui.servers

import com.arkivanov.decompose.ComponentContext

interface ServersComponent

class DefaultServersComponent(
    componentContext: ComponentContext
) : ServersComponent, ComponentContext by componentContext
