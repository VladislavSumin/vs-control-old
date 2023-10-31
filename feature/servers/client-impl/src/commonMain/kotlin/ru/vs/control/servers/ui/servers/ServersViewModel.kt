package ru.vs.control.servers.ui.servers

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.core.decompose.viewmodel.ViewModel
import ru.vs.core.factory_generator.GenerateFactory

@Stable
internal interface ServersViewModel {
    val state: StateFlow<List<Server>>
}

@GenerateFactory(ServersViewModelFactory::class)
internal class ServersViewModelImpl(serversInteractor: ServersInteractor) : ViewModel(), ServersViewModel {
    override val state: StateFlow<List<Server>> = serversInteractor.observeServers()
        .stateIn(emptyList())
}

internal interface ServersViewModelFactory {
    fun create(): ServersViewModelImpl
}
