package ru.vs.control.servers.ui.servers

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.ui.server_card.ServerCardComponentFactory
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.asValue
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.decompose.router.list.childList

internal class ServersComponent(
    private val serverCardComponentFactory: ServerCardComponentFactory,
    private val serverStoreFactory: ServerStoreFactory,
    private val openAddServerScreen: () -> Unit,
    private val openEditServerScreen: (ServerId) -> Unit,
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {
    private val scope = lifecycle.createCoroutineScope()
    private val store: ServersStore = instanceKeeper.getStore { serverStoreFactory.create() }

    val serversList = childList(
        state = store.stateFlow.asValue(scope).map { it.servers },
        childFactory = { server, context ->
            serverCardComponentFactory.create(
                server,
                openEditServerScreen,
                context,
            )
        }
    )

    fun onClickAddServer() = openAddServerScreen()

    @Composable
    override fun Render() = ServersContent(this)
}
