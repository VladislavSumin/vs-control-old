package ru.vs.control.servers.ui.servers

import androidx.compose.runtime.Composable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

class ServersComponent(
    componentContext: DiComponentContext
) : ComposeComponent, DiComponentContext by componentContext {
    private val store: ServersStore = instanceKeeper.getStore {
        ServerStoreFactory(direct.instance()).create()
    }

    internal val state = store.stateFlow

    internal fun onClickAddServer() = store.accept(ServersStore.Intent.AddServer)

    @Composable
    override fun Render() = ServersContent(this)
}
