package ru.vs.control.servers.ui.server_header

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.control.servers_connection.domain.ServerConnectionInteractor
import ru.vs.control.servers_connection.domain.ServersConnectionInteractor
import ru.vs.core.decompose.viewmodel.ViewModel
import ru.vs.core.factory_generator.GenerateFactory

internal interface ServerHeaderViewModel {
    val state: StateFlow<ServerHeaderViewState?>
}

@GenerateFactory(ServerHeaderViewModelFactory::class)
internal class ServerHeaderViewModelImpl(
    private val serversInteractor: ServersInteractor,
    private val serversConnectionInteractor: ServersConnectionInteractor,
) : ViewModel(), ServerHeaderViewModel {
    override val state =
        observeSelectedServer()
            .flatMapLatest { server ->
                if (server == null) flowOf(null)
                else {
                    observeConnectionStatusWithServerInfo(server)
                        .map { createState(server, it) }
                }
            }
            .stateIn(null)

    private fun createState(
        server: Server,
        connectionStatus: ServerConnectionInteractor.ConnectionStatus,
    ): ServerHeaderViewState {
        return ServerHeaderViewState(
            server = server,
            connectionStatus = connectionStatus,
        )
    }

    private suspend fun observeConnectionStatusWithServerInfo(server: Server): Flow<ServerConnectionInteractor.ConnectionStatus> {
        return serversConnectionInteractor.getConnection(server).observeConnectionStatus()
    }

    private fun observeSelectedServer(): Flow<Server?> {
        return serversInteractor.observeSelectedServer()
    }
}

internal interface ServerHeaderViewModelFactory {
    fun create(): ServerHeaderViewModelImpl
}
