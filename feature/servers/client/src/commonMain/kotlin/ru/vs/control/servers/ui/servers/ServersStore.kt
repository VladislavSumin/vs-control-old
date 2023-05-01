package ru.vs.control.servers.ui.servers

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.control.servers.ui.servers.ServersStore.Intent
import ru.vs.control.servers.ui.servers.ServersStore.State

internal interface ServersStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data class DeleteServer(val serverId: ServerId) : Intent()
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val servers: List<Server>) : State()
    }
}

internal class ServerStoreFactory(
    private val storeFactory: StoreFactory,
    private val serversInteractor: ServersInteractor,
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
        data class ServersListUpdated(val servers: List<Server>) : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch {
                serversInteractor.observeServers()
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
            return State.Loaded(servers = (msg as Msg.ServersListUpdated).servers)
        }
    }
}
