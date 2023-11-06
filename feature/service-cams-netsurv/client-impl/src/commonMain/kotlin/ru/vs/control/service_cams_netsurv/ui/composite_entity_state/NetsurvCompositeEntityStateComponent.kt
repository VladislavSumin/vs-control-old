package ru.vs.control.service_cams_netsurv.ui.composite_entity_state

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.factory_generator.GenerateEntityStateComponentFactory
import ru.vs.control.entities.ui.entities.entity_state.BaseEntityStateComponent
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvCompositeEntityState

@GenerateEntityStateComponentFactory
internal class NetsurvCompositeEntityStateComponent(
    private val entityStateViewModelFactory: NetsurvCompositeEntityStateViewModelFactory,
    state: StateFlow<Entity<NetsurvCompositeEntityState>>,
    context: ComponentContext,
) : BaseEntityStateComponent<NetsurvCompositeEntityState>(state, context) {
    val viewModel: NetsurvCompositeEntityStateViewModel =
        instanceKeeper.getOrCreate { entityStateViewModelFactory.create(state) }

    @Composable
    override fun Render(modifier: Modifier) = NetsurvLiveVideoStreamEntityStateContent(this)
}
