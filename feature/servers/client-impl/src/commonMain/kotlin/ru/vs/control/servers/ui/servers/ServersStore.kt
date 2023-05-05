package ru.vs.control.servers.ui.servers

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vs.control.about_server.domain.AboutServerInteractor
import ru.vs.control.about_server.domain.AboutServerInteractor.ConnectionStatusWithServerInfo
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.control.servers.ui.servers.ServersStore.Intent
import ru.vs.control.servers.ui.servers.ServersStore.ServerUiItem
import ru.vs.control.servers.ui.servers.ServersStore.State

internal interface ServersStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        /**
         * Set current selected server
         */
        data class SelectServer(val serverId: ServerId) : Intent()

        /**
         * Delete server
         */
        data class DeleteServer(val serverId: ServerId) : Intent()
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val servers: List<ServerUiItem>) : State()
    }

    data class ServerUiItem(
        /**
         * Server model
         */
        val server: Server,

        /**
         * Current server connection status
         */
        val connectionStatus: ConnectionStatusWithServerInfo,

        /**
         * Is this server selected as default server
         */
        val isSelected: Boolean,
    )
}

internal class ServerStoreFactory(
    private val storeFactory: StoreFactory,
    private val serversInteractor: ServersInteractor,
    private val aboutServerInteractor: AboutServerInteractor,
) {
    fun create(): ServersStore = object :
        ServersStore,
        Store<Intent, State, Nothing> by storeFactory.create(
            name = ServersStore::class.simpleName,
            initialState = State.Loading,
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class ServersListUpdated(val servers: List<ServerUiItem>) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch {
                observeServerUiState()
                    .map { Msg.ServersListUpdated(it) }
                    .collect(::dispatch)
            }
        }

        private fun observeServerUiState(): Flow<List<ServerUiItem>> {
            return combine(
                serversInteractor.observeServers(),
                serversInteractor.observeSelectedServerId(),
            ) { servers, selectedServerId -> servers to selectedServerId }
                .flatMapLatest { (servers, selectedServerId) ->
                    combine(
                        servers.map { server ->
                            aboutServerInteractor.observeConnectionStatusWithServerInfo(server).map { server to it }
                        }
                    ) {
                        it.map { (server, connectionStatus) ->
                            ServerUiItem(server, connectionStatus, server.id == selectedServerId)
                        }
                    }
                }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.SelectServer -> scope.launch { serversInteractor.setSelectedServer(intent.serverId) }
                is Intent.DeleteServer -> scope.launch { serversInteractor.deleteServer(intent.serverId) }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State {
            return when (msg) {
                is Msg.ServersListUpdated -> State.Loaded(msg.servers)
            }
        }
    }
}
