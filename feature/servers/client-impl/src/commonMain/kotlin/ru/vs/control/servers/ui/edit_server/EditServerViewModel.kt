package ru.vs.control.servers.ui.edit_server

import kotlinx.coroutines.launch
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.control.servers.ui.edit_server.EditServerViewModel.Event
import ru.vs.control.servers.ui.edit_server.EditServerViewModel.Intent
import ru.vs.core.decompose.viewmodel.MviViewModel
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(EditServerViewModelFactory::class)
internal class EditServerViewModel(
    private val serversInteractor: ServersInteractor,
    private val serverId: ServerId?,
) : MviViewModel<EditServerViewState, Intent, Event>() {

    init {
        viewModelScope.launch {
            if (serverId != null) {
                val server = serversInteractor.getServer(serverId)
                receiveIntent(Intent.UpdateName(server.name))
                receiveIntent(Intent.UpdateHost(server.host))
            }
        }
    }

    override fun createInitialSate(): EditServerViewState {
        return EditServerViewState(
            name = "",
            host = "",
            port = 8080,
            isEditMode = false,
            isSaving = false,
        )
    }

    override suspend fun processIntent(intent: Intent): EditServerViewState {
        return when (intent) {
            Intent.Back -> {
                produceEvent(Event.CloseScreen)
                state
            }

            Intent.Save -> {
                viewModelScope.launch {
                    val server = Server(serverId ?: 0L, state.name, state.host, state.port)
                    if (server.id == 0L) serversInteractor.addServer(server)
                    else serversInteractor.updateServer(server)
                    produceEvent(Event.CloseScreen)
                }
                state.copy(isSaving = true)
            }

            is Intent.UpdateHost -> state.copy(host = intent.host)
            is Intent.UpdateName -> state.copy(name = intent.name)
        }
    }

    fun accept(intent: Intent) {
        viewModelScope.launch { receiveIntent(intent) }
    }

    sealed interface Intent {
        data class UpdateName(val name: String) : Intent
        data class UpdateHost(val host: String) : Intent
        object Back : Intent
        object Save : Intent
    }

    sealed interface Event {
        object CloseScreen : Event
    }
}

internal interface EditServerViewModelFactory {
    fun create(serverId: ServerId? = null): EditServerViewModel
}