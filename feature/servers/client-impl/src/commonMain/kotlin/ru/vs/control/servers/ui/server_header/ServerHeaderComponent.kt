package ru.vs.control.servers.ui.server_header

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers_connection.domain.ServerConnectionInteractor
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.factory_generator.GenerateFactory

@Stable
internal interface ServerHeaderComponent {
    val state: StateFlow<ServerHeaderViewState?>
}

@GenerateFactory(ServerHeaderComponentFactory::class)
internal class ServerHeaderComponentImpl(
    serverHeaderViewModelFactory: ServerHeaderViewModelFactory,
    context: ComponentContext,
) : ServerHeaderComponent, ComposeComponent, ComponentContext by context {
    private val viewModel: ServerHeaderViewModel = instanceKeeper.getOrCreate { serverHeaderViewModelFactory.create() }

    override val state = viewModel.state

    @Composable
    override fun Render(modifier: Modifier) = ServerHeaderContent(this, modifier)
}

internal class ServerHeaderComponentPreview(previewType: PreviewType, id: Int) : ServerHeaderComponent {
    override val state: StateFlow<ServerHeaderViewState> = MutableStateFlow(
        when (previewType) {
            PreviewType.Connecting -> ServerHeaderViewState(
                server = createServer(id),
                connectionStatus = ServerConnectionInteractor.ConnectionStatus.Connecting,
            )

            PreviewType.Connected -> ServerHeaderViewState(
                server = createServer(id),
                connectionStatus = ServerConnectionInteractor.ConnectionStatus.Connected,
            )

            PreviewType.Reconnecting -> ServerHeaderViewState(
                server = createServer(id),
                connectionStatus = ServerConnectionInteractor.ConnectionStatus.Reconnecting(
                    RuntimeException("Error message")
                ),
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
        Reconnecting,
    }
}
