package ru.vs.control.entities.ui.entities

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.entities.dto.EntityDto
import ru.vs.control.entities.ui.entities.EntitiesStore.Intent
import ru.vs.control.entities.ui.entities.EntitiesStore.Label
import ru.vs.control.entities.ui.entities.EntitiesStore.State

internal interface EntitiesStore : Store<Intent, State, Label> {

    sealed interface Intent

    data class State(val entities: List<EntityDto>)

    sealed interface Label
}

internal class EntitiesStoreFactory(
    private val storeFactory: StoreFactory,
    private val entitiesInteractor: EntitiesInteractor,
) {

    fun create(): EntitiesStore =
        object :
            EntitiesStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "EntitiesStore",
                initialState = State(emptyList()),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Msg {
        data class EntitiesListUpdated(val entities: List<EntityDto>) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            // no intents for now
        }

        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch {
                entitiesInteractor.observeEntities()
                    .map { Msg.EntitiesListUpdated(it) }
                    .collect(::dispatch)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.EntitiesListUpdated -> copy(entities = msg.entities)
            }
    }
}
