package ru.vs.control.entities.ui.entities

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponent
import ru.vs.control.entities.ui.entities.unknown_entity_state.UnknownEntityStateComponent
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.asNavigationSource
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.decompose.router.list.childList
import ru.vs.core.decompose.router.list.childListWithState

internal class EntitiesComponent(
    entitiesStoreFactory: EntitiesStoreFactory,
    context: ComponentContext
) : ComposeComponent, ComponentContext by context {
    private val scope = lifecycle.createCoroutineScope()
    private val store: EntitiesStore = instanceKeeper.getStore { entitiesStoreFactory.create() }

    val entitiesList: Value<List<EntityStateComponent>> = childListWithState(
        source = store.stateFlow.map { it.entities }.asNavigationSource(scope),
        idSelector = { it.id },
        childFactory = { entityState, context ->
            UnknownEntityStateComponent(entityState, context)
        }
    )

    @Composable
    override fun Render() = EntitiesContent(this)
}
