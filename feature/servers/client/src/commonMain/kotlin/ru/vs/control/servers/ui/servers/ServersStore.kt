package ru.vs.control.servers.ui.servers

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.vs.control.servers.ui.servers.ServersStore.Intent
import ru.vs.control.servers.ui.servers.ServersStore.State

internal interface ServersStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        object AddServer : Intent()
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val servers: List<Server>) : State()

        data class Server(val name: String)
    }
}

internal class ServerStoreFactory(private val storeFactory: StoreFactory) {
    fun create(): ServersStore =
        object :
            ServersStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = ServersStore::class.simpleName,
                initialState = State.Loaded(emptyList()),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data class ServersListUpdated(val servers: List<State.Server>) : Msg()
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Nothing, State, Msg, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            val servers = ((getState() as? State.Loaded)?.servers ?: emptyList()) + State.Server("Server name")
            dispatch(Msg.ServersListUpdated(servers))
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State {
            return State.Loaded(servers = (msg as Msg.ServersListUpdated).servers)
        }
    }
}
