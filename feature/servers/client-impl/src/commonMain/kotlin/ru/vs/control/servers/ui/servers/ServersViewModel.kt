package ru.vs.control.servers.ui.servers

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.core.decompose.viewmodel.ViewModel
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory
class ServersViewModel(serversInteractor: ServersInteractor) : ViewModel() {
    val state: StateFlow<List<Server>> = serversInteractor.observeServers()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
