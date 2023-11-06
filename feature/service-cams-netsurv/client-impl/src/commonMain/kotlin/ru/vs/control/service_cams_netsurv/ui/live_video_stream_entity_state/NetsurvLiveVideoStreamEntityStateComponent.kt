package ru.vs.control.service_cams_netsurv.ui.live_video_stream_entity_state

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.factory_generator.GenerateEntityStateComponentFactory
import ru.vs.control.entities.ui.entities.entity_state.BaseEntityStateComponent
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvLiveVideoStreamEntityState

@GenerateEntityStateComponentFactory
internal class NetsurvLiveVideoStreamEntityStateComponent(
    private val entityStateViewModelFactory: NetsurvLiveVideoStreamEntityStateViewModelFactory,
    state: StateFlow<Entity<NetsurvLiveVideoStreamEntityState>>,
    context: ComponentContext,
) : BaseEntityStateComponent<NetsurvLiveVideoStreamEntityState>(state, context) {
    val viewModel: NetsurvLiveVideoStreamEntityStateViewModel =
        instanceKeeper.getOrCreate { entityStateViewModelFactory.create(state) }

    @Composable
    override fun Render(modifier: Modifier) = NetsurvLiveVideoStreamEntityStateContent(this)
}
