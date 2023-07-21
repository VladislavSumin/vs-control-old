package ru.vs.control.servers.ui.server_card

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.vs.control.about_server.domain.AboutServerInteractor
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.core.decompose.viewmodel.ViewModel
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(ServerCardViewModelFactory::class)
internal class ServerCardViewModel(
    private val serversInteractor: ServersInteractor,
    aboutServerInteractor: AboutServerInteractor,
    private val server: Server
) : ViewModel() {
    val state = combine(
        serversInteractor.observeSelectedServerId()
            .map { it == server.id }
            .distinctUntilChanged(),
        aboutServerInteractor.observeConnectionStatusWithServerInfo(server),
    ) { isCurrentServerSelected, connectionStatus ->
        ServerCardViewState(
            server = server,
            connectionStatus = connectionStatus,
            isSelected = isCurrentServerSelected,
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ServerCardViewState(
                server = server,
                connectionStatus = AboutServerInteractor.ConnectionStatusWithServerInfo.Connecting,
                isSelected = false,
            )
        )

    fun deleteCurrentServer() {
        viewModelScope.launch {
            serversInteractor.deleteServer(server.id)
        }
    }

    fun selectCurrentServer() {
        viewModelScope.launch {
            serversInteractor.setSelectedServer(server.id)
        }
    }
}

internal interface ServerCardViewModelFactory {
    fun create(server: Server): ServerCardViewModel
}
