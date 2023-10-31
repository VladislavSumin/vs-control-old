package ru.vs.control.servers.ui.server_card

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.vs.control.about_server.domain.AboutServerInteractor
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.core.decompose.viewmodel.ViewModel
import ru.vs.core.factory_generator.GenerateFactory

@Stable
internal interface ServerCardViewModel {
    val state: StateFlow<ServerCardViewState>
    fun deleteCurrentServer()
    fun selectCurrentServer()
}

@GenerateFactory(ServerCardViewModelFactory::class)
internal class ServerCardViewModelImpl(
    private val serversInteractor: ServersInteractor,
    private val aboutServerInteractor: AboutServerInteractor,
    private val server: Server
) : ViewModel(), ServerCardViewModel {
    override val state = combine(
        observeIsCurrentServerSelected(),
        observeConnectionStatusWithServerInfo(),
        ::createState
    ).stateIn(createInitialState())

    override fun deleteCurrentServer() = launch {
        serversInteractor.deleteServer(server.id)
    }

    override fun selectCurrentServer() = launch {
        serversInteractor.setSelectedServer(server.id)
    }

    private fun createState(
        isCurrentServerSelected: Boolean,
        connectionStatus: AboutServerInteractor.ConnectionStatusWithServerInfo,
    ): ServerCardViewState {
        return ServerCardViewState(
            server = server,
            connectionStatus = connectionStatus,
            isSelected = isCurrentServerSelected,
        )
    }

    private fun observeConnectionStatusWithServerInfo(): Flow<AboutServerInteractor.ConnectionStatusWithServerInfo> {
        return aboutServerInteractor.observeConnectionStatusWithServerInfo(server)
    }

    /**
     * Возвращает [Flow] является ли текущий сервер [server] сервером по умолчанию
     */
    private fun observeIsCurrentServerSelected(): Flow<Boolean> {
        return serversInteractor.observeSelectedServerId()
            .map { it == server.id }
            .distinctUntilChanged()
    }

    private fun createInitialState(): ServerCardViewState {
        return ServerCardViewState(
            server = server,
            connectionStatus = AboutServerInteractor.ConnectionStatusWithServerInfo.Connecting,
            isSelected = false,
        )
    }
}

internal interface ServerCardViewModelFactory {
    fun create(server: Server): ServerCardViewModelImpl
}
