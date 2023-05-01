package ru.vs.control.servers.ui.servers

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.control.servers.ui.servers.ServersStore.Intent
import ru.vs.control.servers.ui.servers.ServersStore.ServerUiItem
import ru.vs.control.servers.ui.servers.ServersStore.State
import ru.vs.control.servers_connection.domain.ServerConnectionInteractor.ConnectionStatus
import ru.vs.control.servers_connection.domain.ServersConnectionInteractor

internal interface ServersStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data class DeleteServer(val serverId: ServerId) : Intent()
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val servers: List<ServerUiItem>) : State()
    }

    data class ServerUiItem(val server: Server, val connectionStatus: ConnectionStatus)
}

internal class ServerStoreFactory(
    private val storeFactory: StoreFactory,
    private val serversInteractor: ServersInteractor,
    private val serversConnectionInteractor: ServersConnectionInteractor,
) {
    fun create(): ServersStore =
        object :
            ServersStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = ServersStore::class.simpleName,
                initialState = State.Loaded(emptyList()),
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
                serversInteractor.observeServers()
                    .flatMapLatest { servers ->
                        channelFlow {
                            val connectionStateUpdateChannel = Channel<Pair<ServerId, ConnectionStatus>>()
                            val uiServers: MutableMap<ServerId, ServerUiItem> = servers
                                .map { ServerUiItem(it, ConnectionStatus.Connecting) }
                                .associateBy { it.server.id }
                                .toMutableMap()
                            send(uiServers.values.toList())

                            servers.forEach { server: Server ->
                                launch {
                                    val connection = serversConnectionInteractor.getConnection(server)
                                    connection.observeConnectionStatus()
                                        .collect {
                                            connectionStateUpdateChannel.send(server.id to it)
                                        }
                                }
                            }

                            connectionStateUpdateChannel.consumeEach { (serverId, connectionInfo) ->
                                uiServers[serverId] = uiServers[serverId]!!.copy(connectionStatus = connectionInfo)
                                send(uiServers.values.toList())
                            }
                        }
                    }
                    .map { Msg.ServersListUpdated(it) }
                    .collect(::dispatch)
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
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
