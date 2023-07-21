package ru.vs.control.servers.ui.server_card

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(ServerCardComponentFactory::class)
internal class ServerCardComponent(
    serverCardViewModelFactory: ServerCardViewModelFactory,
    private val server: Server,
    private val openEditServerScreen: (ServerId) -> Unit,
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {
    private val viewModel: ServerCardViewModel = instanceKeeper.getOrCreate { serverCardViewModelFactory.create(server) }

    val state = viewModel.state

    fun onClickSelectServer() = viewModel.selectCurrentServer()
    fun onClickEditServer() = openEditServerScreen(server.id)
    fun onClickDeleteServer() = viewModel.deleteCurrentServer()

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
