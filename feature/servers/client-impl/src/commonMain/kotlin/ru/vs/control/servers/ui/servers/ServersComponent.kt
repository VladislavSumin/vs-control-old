package ru.vs.control.servers.ui.servers

import androidx.compose.runtime.Composable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.ui.servers.ServersStore.Intent
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

internal class ServersComponent(
    componentContext: DiComponentContext,
    private val openAddServerScreen: () -> Unit,
    private val openEditServerScreen: (ServerId) -> Unit,
) : ComposeComponent, DiComponentContext by componentContext {
    private val store: ServersStore = instanceKeeper.getStore {
        direct.instance<ServerStoreFactory>().create()
    }

    val state = store.stateFlow

    fun onClickAddServer() = openAddServerScreen()
    fun onClickSelectServer(serverId: ServerId) = store.accept(Intent.SelectServer(serverId))
    fun onClickEditServer(serverId: ServerId) = openEditServerScreen(serverId)
    fun onClickDeleteServer(serverId: ServerId) = store.accept(Intent.DeleteServer(serverId))

    @Composable
    override fun Render() = ServersContent(this)
}
