package ru.vs.control.servers.ui.servers

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.vs.core.decompose.ComposeComponent

class ServersComponent(
    componentContext: ComponentContext
) : ComposeComponent, ComponentContext by componentContext {
    private val store: ServersStore = instanceKeeper.getStore {
        ServerStoreFactory(DefaultStoreFactory()).create()
    }

    internal val state = store.stateFlow

    @Composable
    override fun Render() = ServersContent(this)
}
