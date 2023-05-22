package ru.vs.control.servers.ui.server_card

import androidx.compose.runtime.Composable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

internal class ServerCardComponent(
    private val server: Server,
    context: DiComponentContext,
    private val openEditServerScreen: (ServerId) -> Unit,
) : ComposeComponent, DiComponentContext by context {
    private val store: ServerCardStore = instanceKeeper.getStore {
        direct.instance<ServerCardStoreFactory>().create(server)
    }

    val state = store.stateFlow

    fun onClickSelectServer() = store.accept(ServerCardStore.Intent.SelectServer)
    fun onClickEditServer() = openEditServerScreen(server.id)
    fun onClickDeleteServer() = store.accept(ServerCardStore.Intent.DeleteServer)

    @Composable
    override fun Render() = ServerCardContent(this)
}
