package ru.vs.control.entities.ui.entities

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponent
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactoryRegistry
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.asValue
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.decompose.router.list.childListWithState

internal class EntitiesComponent(
    private val entityStateComponentFactoryRegistry: EntityStateComponentFactoryRegistry,
    entitiesViewModelFactory: EntitiesViewModelFactory,
    context: ComponentContext
) : ComposeComponent, ComponentContext by context {
    private val scope = lifecycle.createCoroutineScope()
    private val viewModel: EntitiesViewModel = instanceKeeper.getOrCreate { entitiesViewModelFactory.create() }

    val entitiesList: Value<List<EntityStateComponent<*>>> = childListWithState(
        state = viewModel.state.asValue(scope),
        idSelector = { it.id },
        childFactory = { entityState, context ->
            entityStateComponentFactoryRegistry.create(entityState, context)
        }
    )

    @Composable
    override fun Render() = EntitiesContent(this)
}
