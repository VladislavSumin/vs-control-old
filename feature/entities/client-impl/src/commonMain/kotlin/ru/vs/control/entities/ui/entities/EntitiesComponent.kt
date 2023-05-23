package ru.vs.control.entities.ui.entities

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.control.entities.ui.entities.unknown_entity_state.UnknownEntityStateComponent
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext
import ru.vs.core.decompose.asNavigationSource
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.decompose.router.list.childList

internal class EntitiesComponent(context: DiComponentContext) : ComposeComponent, DiComponentContext by context {
    private val scope = lifecycle.createCoroutineScope()
    private val store: EntitiesStore = instanceKeeper.getStore {
        direct.instance<EntitiesStoreFactory>().create()
    }

    val entitiesList: Value<List<Child.Created<*, UnknownEntityStateComponent>>> = childList(
        source = store.stateFlow.map { it.entities }.asNavigationSource(scope),
        childFactory = { entity, context ->
            val diContext = DiComponentContext(context, di)
            UnknownEntityStateComponent(MutableStateFlow(entity), diContext)
        }
    )

    @Composable
    override fun Render() = EntitiesContent(this)
}
