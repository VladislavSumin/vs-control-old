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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vs.control.about_server.domain.AboutServerInteractor
import ru.vs.control.about_server.domain.ServerInfo
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
        data class SelectServer(val serverId: ServerId) : Intent()
        data class DeleteServer(val serverId: ServerId) : Intent()
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val servers: List<ServerUiItem>) : State()
    }

    data class ServerUiItem(
        val server: Server,
        val connectionStatus: ConnectionStatus,
        val serverInfo: ServerInfo?,
        val isSelected: Boolean,
    )
}

internal class ServerStoreFactory(
    private val storeFactory: StoreFactory,
    private val serversInteractor: ServersInteractor,
    private val serversConnectionInteractor: ServersConnectionInteractor,
    private val aboutServerInteractor: AboutServerInteractor,
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
                combine(
                    serversInteractor.observeServers(),
                    serversInteractor.observeSelectedServerId(),
                ) { servers, selectedServerId -> servers to selectedServerId }
                    .flatMapLatest { (servers, selectedServerId) ->
                        channelFlow {
                            val connectionStateUpdateChannel =
                                Channel<Pair<ServerId, Pair<ConnectionStatus, ServerInfo?>>>()
                            val uiServers: MutableMap<ServerId, ServerUiItem> = servers
                                .map { ServerUiItem(it, ConnectionStatus.Connecting, null, it.id == selectedServerId) }
                                .associateBy { it.server.id }
                                .toMutableMap()
                            send(uiServers.values.toList())

                            servers.forEach { server: Server ->
                                launch {
                                    val connection = serversConnectionInteractor.getConnection(server)
                                    connection.observeConnectionStatus()
                                        .collect { connectionStatus ->
                                            val serverInfo = if (connectionStatus is ConnectionStatus.Connected) {
                                                aboutServerInteractor.getServerInfo(server)
                                            } else null
                                            val data = connectionStatus to serverInfo
                                            connectionStateUpdateChannel.send(server.id to data)
                                        }
                                }
                            }

                            connectionStateUpdateChannel.consumeEach { (serverId, data) ->
                                val (connectionStatus, serverInfo) = data
                                uiServers[serverId] = uiServers[serverId]!!
                                    .copy(
                                        connectionStatus = connectionStatus,
                                        serverInfo = serverInfo
                                    )
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
