package ru.vs.control.servers.ui.server_card

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.vs.control.about_server.domain.AboutServerInteractor
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.control.servers.ui.server_card.ServerCardStore.Intent
import ru.vs.control.servers.ui.server_card.ServerCardStore.Label
import ru.vs.control.servers.ui.server_card.ServerCardStore.State

internal interface ServerCardStore : Store<Intent, State, Label> {

    sealed interface Intent {
        /**
         * Set current selected server
         */
        object SelectServer : Intent

        /**
         * Delete server
         */
        object DeleteServer : Intent
    }

    data class State(
        /**
         * Server model
         */
        val server: Server,

        /**
         * Current server connection status
         */
        val connectionStatus: AboutServerInteractor.ConnectionStatusWithServerInfo,

        /**
         * Is this server selected as default server
         */
        val isSelected: Boolean,
    )

    sealed interface Label
}

internal class ServerCardStoreFactory(
    private val storeFactory: StoreFactory,
    private val serversInteractor: ServersInteractor,
    private val aboutServerInteractor: AboutServerInteractor,
) {

    fun create(server: Server): ServerCardStore =
        object :
            ServerCardStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "ServerCardStore",
                initialState = State(server, AboutServerInteractor.ConnectionStatusWithServerInfo.Connecting, false),
                bootstrapper = SimpleBootstrapper(Action.SetServer(server)),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class SetServer(val server: Server) : Action
    }

    private sealed interface Msg {
        data class IsSelectedUpdated(val isSelected: Boolean) : Msg
        data class ConnectionStatusUpdated(
            val connectionStatus: AboutServerInteractor.ConnectionStatusWithServerInfo
        ) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            val serverId = getState().server.id
            when (intent) {
                is Intent.SelectServer -> scope.launch { serversInteractor.setSelectedServer(serverId) }
                is Intent.DeleteServer -> scope.launch { serversInteractor.deleteServer(serverId) }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            val server = (action as Action.SetServer).server
            dispatchIsSelectedState(server)
            dispatchServerConnectionStatusInfo(server)
        }

        /**
         * Dispatch [State.isSelected]
         */
        private fun dispatchIsSelectedState(server: Server) {
            scope.launch {
                serversInteractor.observeSelectedServerId().collectLatest { selectedServerId ->
                    dispatch(Msg.IsSelectedUpdated(selectedServerId == server.id))
                }
            }
        }

        /**
         * Dispatch [State.connectionStatus]
         */
        private fun dispatchServerConnectionStatusInfo(server: Server) {
            scope.launch {
                aboutServerInteractor.observeConnectionStatusWithServerInfo(server).collectLatest { connectionStatus ->
                    dispatch(Msg.ConnectionStatusUpdated(connectionStatus))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.IsSelectedUpdated -> copy(isSelected = msg.isSelected)
                is Msg.ConnectionStatusUpdated -> copy(connectionStatus = msg.connectionStatus)
            }
    }
}
