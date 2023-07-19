package ru.vs.control.servers.ui.server_card

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(ServerCardComponentFactory::class)
internal class ServerCardComponent(
    private val serverCardStoreFactory: ServerCardStoreFactory,
    private val server: Server,
    private val openEditServerScreen: (ServerId) -> Unit,
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {
    private val store: ServerCardStore = instanceKeeper.getStore { serverCardStoreFactory.create(server) }

    val state = store.stateFlow

    fun onClickSelectServer() = store.accept(ServerCardStore.Intent.SelectServer)
    fun onClickEditServer() = openEditServerScreen(server.id)
    fun onClickDeleteServer() = store.accept(ServerCardStore.Intent.DeleteServer)

    @Composable
    override fun Render() = ServerCardContent(this)
}

internal interface ServerCardComponentFactory {
    fun create(
        server: Server,
        openEditServerScreen: (ServerId) -> Unit,
        context: ComponentContext,
    ): ServerCardComponent
}
