package ru.vs.control.main_screen.ui.main_screen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.vs.control.main_screen.ui.main_screen.MainScreenStore.Intent
import ru.vs.control.main_screen.ui.main_screen.MainScreenStore.Label
import ru.vs.control.main_screen.ui.main_screen.MainScreenStore.State

internal interface MainScreenStore : Store<Intent, State, Label> {

    sealed interface Intent

    data class State(val unit: Unit = Unit)

    sealed interface Label
}

internal class MainScreenStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): MainScreenStore =
        object :
            MainScreenStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "MainScreenStore",
                initialState = State(),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action

    private sealed interface Msg

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            // no op
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            // no op
        }

        override fun executeAction(action: Action, getState: () -> State) {
            // no op
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                else -> this
            }
    }
}
