package ru.vs.control.servers.ui.edit_server

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.control.servers.ui.edit_server.EditServerStore.Intent
import ru.vs.control.servers.ui.edit_server.EditServerStore.Label
import ru.vs.control.servers.ui.edit_server.EditServerStore.State

internal interface EditServerStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class UpdateName(val name: String) : Intent
        data class UpdateHost(val host: String) : Intent
        object Save : Intent
    }

    data class State(
        val id: ServerId = 0,
        val name: String,
        val host: String,
        val port: Int,
    )

    sealed interface Label {
        object CloseScreen : Label
    }
}

internal class EditServerStoreFactory(
    private val storeFactory: StoreFactory,
    private val serversInteractor: ServersInteractor,
) {

    fun create(serverId: Long?): EditServerStore =
        object :
            EditServerStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "AddServerStore",
                initialState = State(name = "", host = "", port = 8080),
                bootstrapper = BootstrapperImpl(serverId),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class LoadServer(val serverId: Long) : Action
        object CreateNewServer : Action
    }

    private sealed interface Msg {
        data class UpdateId(val id: ServerId) : Msg
        data class UpdateName(val name: String) : Msg
        data class UpdateHost(val host: String) : Msg
        // TODO add port setup
    }

    private class BootstrapperImpl(val serverId: Long?) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            val action = if (serverId != null) {
                Action.LoadServer(serverId)
            } else {
                Action.CreateNewServer
            }
            dispatch(action)
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.Save -> {
                    scope.launch {
                        val state = getState()
                        val server = Server(state.id, state.name, state.host, state.port)
                        if (server.id == 0L) serversInteractor.addServer(server)
                        else serversInteractor.updateServer(server)
                        publish(Label.CloseScreen)
                    }
                }

                is Intent.UpdateName -> dispatch(Msg.UpdateName(intent.name))
                is Intent.UpdateHost -> dispatch(Msg.UpdateHost(intent.host))
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            scope.launch {
                when (action) {
                    Action.CreateNewServer -> Unit
                    is Action.LoadServer -> scope.launch {
                        val server = serversInteractor.getServer(action.serverId)
                        dispatch(Msg.UpdateId(server.id))
                        dispatch(Msg.UpdateName(server.name))
                        dispatch(Msg.UpdateHost(server.host))
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State {
            return when (message) {
                is Msg.UpdateId -> copy(id = message.id)
                is Msg.UpdateName -> copy(name = message.name)
                is Msg.UpdateHost -> copy(host = message.host)
            }
        }
    }
}
