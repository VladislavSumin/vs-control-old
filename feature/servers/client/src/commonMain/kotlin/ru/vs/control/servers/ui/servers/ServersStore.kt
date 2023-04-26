package ru.vs.control.servers.ui.servers

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.store.create
import ru.vs.control.servers.ui.servers.ServersStore.State

interface ServersStore : Store<Nothing, State, Nothing> {
    sealed class State {
        object Loading : State()
    }
}

class ServerStoreFactory(private val storeFactory: StoreFactory) {
    fun create(): ServersStore =
        object :
            ServersStore,
            Store<Nothing, State, Nothing> by storeFactory.create(
                name = ServersStore::class.simpleName,
                initialState = State.Loading,
                reducer = ReducerImpl
            ) {}

    private object ReducerImpl : Reducer<State, Nothing> {
        override fun State.reduce(msg: Nothing): State {
            return this
        }
    }
}
