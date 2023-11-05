package ru.vs.control.servers.ui.server_card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.about_server.domain.AboutServerInteractor
import ru.vs.control.about_server.domain.ServerInfo
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.factory_generator.GenerateFactory

@Stable
internal interface ServerCardComponent {
    val state: StateFlow<ServerCardViewState>

    fun onClickSelectServer() {}
    fun onClickEditServer() {}
    fun onClickDeleteServer() {}
}

@GenerateFactory(ServerCardComponentFactory::class)
internal class ServerCardComponentImpl(
    serverCardViewModelFactory: ServerCardViewModelFactory,
    private val server: Server,
    private val openEditServerScreen: (ServerId) -> Unit,
    context: ComponentContext,
) : ServerCardComponent, ComposeComponent, ComponentContext by context {
    private val viewModel: ServerCardViewModel = instanceKeeper.getOrCreate {
        serverCardViewModelFactory.create(
            server
        )
    }

    override val state = viewModel.state

    override fun onClickSelectServer() = viewModel.selectCurrentServer()
    override fun onClickEditServer() = openEditServerScreen(server.id)
    override fun onClickDeleteServer() = viewModel.deleteCurrentServer()

    @Composable
    override fun Render(modifier: Modifier) = ServerCardContent(this)
}

internal interface ServerCardComponentFactory {
    fun create(
        server: Server,
        openEditServerScreen: (ServerId) -> Unit,
        context: ComponentContext,
    ): ServerCardComponentImpl
}

internal class ServerCardComponentPreview(previewType: PreviewType, id: Int) : ServerCardComponent {
    override val state: StateFlow<ServerCardViewState> = MutableStateFlow(
        when (previewType) {
            PreviewType.Connecting -> ServerCardViewState(
                server = createServer(id),
                connectionStatus = AboutServerInteractor.ConnectionStatusWithServerInfo.Connecting,
                isSelected = true,
            )

            PreviewType.Connected -> ServerCardViewState(
                server = createServer(id),
                connectionStatus = AboutServerInteractor.ConnectionStatusWithServerInfo.Connected(
                    serverInfo = ServerInfo(version = "1.2.3")
                ),
                isSelected = false,
            )

            PreviewType.FailedToGetServerInfo -> ServerCardViewState(
                server = createServer(id),
                connectionStatus = AboutServerInteractor.ConnectionStatusWithServerInfo.FailedToGetServerInfo(
                    RuntimeException("Error message")
                ),
                isSelected = false,
            )

            PreviewType.Reconnecting -> ServerCardViewState(
                server = createServer(id),
                connectionStatus = AboutServerInteractor.ConnectionStatusWithServerInfo.Reconnecting(
                    RuntimeException("Error message")
                ),
                isSelected = false,
            )
        }
    )

    private fun createServer(id: Int) = Server(
        id = id.toLong(),
        name = "Server name",
        host = "server.host.vs",
        port = 80,
    )

    enum class PreviewType {
        Connecting,
        Connected,
        FailedToGetServerInfo,
        Reconnecting,
    }
}
