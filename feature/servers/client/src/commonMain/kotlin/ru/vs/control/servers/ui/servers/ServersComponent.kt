package ru.vs.control.servers.ui.servers

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kotlinx.coroutines.flow.StateFlow

interface ServersComponent {
    val state: StateFlow<ServersStore.State>
}

class DefaultServersComponent(
    componentContext: ComponentContext
) : ServersComponent, ComponentContext by componentContext {
    private val store: ServersStore = instanceKeeper.getStore {
        ServerStoreFactory(DefaultStoreFactory()).create()
    }

    override val state = store.stateFlow
}
