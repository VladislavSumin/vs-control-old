package ru.vs.control.servers.ui.servers

import androidx.compose.runtime.Composable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.map
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.ui.server_card.ServerCardComponent
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext
import ru.vs.core.decompose.asNavigationSource
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.decompose.router.list.childList

internal class ServersComponent(
    componentContext: DiComponentContext,
    private val openAddServerScreen: () -> Unit,
    private val openEditServerScreen: (ServerId) -> Unit,
) : ComposeComponent, DiComponentContext by componentContext {
    private val scope = lifecycle.createCoroutineScope()
    private val store: ServersStore = instanceKeeper.getStore {
        direct.instance<ServerStoreFactory>().create()
    }

    val serversList = childList(
        source = store.stateFlow.map { it.servers }.asNavigationSource(scope),
        initialState = { store.state.servers },
        childFactory = { server, context ->
            ServerCardComponent(
                server,
                DiComponentContext(context, di),
                openEditServerScreen
            )
        }
    )

    fun onClickAddServer() = openAddServerScreen()

    @Composable
    override fun Render() = ServersContent(this)
}
