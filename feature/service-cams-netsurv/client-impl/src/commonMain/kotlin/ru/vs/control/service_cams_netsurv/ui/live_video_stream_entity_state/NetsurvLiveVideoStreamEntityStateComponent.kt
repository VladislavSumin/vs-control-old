package ru.vs.control.service_cams_netsurv.ui.live_video_stream_entity_state

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.ui.entities.entity_state.BaseEntityStateComponent
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponent
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactory
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvLiveVideoStreamEntityState
import kotlin.reflect.KClass

internal class NetsurvLiveVideoStreamEntityStateComponent(
    private val entityStateViewModelFactory: NetsurvLiveVideoStreamEntityStateViewModelFactory,
    state: StateFlow<Entity<NetsurvLiveVideoStreamEntityState>>,
    context: ComponentContext,
) : BaseEntityStateComponent<NetsurvLiveVideoStreamEntityState>(state, context) {
    val viewModel: NetsurvLiveVideoStreamEntityStateViewModel =
        instanceKeeper.getOrCreate { entityStateViewModelFactory.create(state) }

    @Composable
    override fun Render() = NetsurvLiveVideoStreamEntityStateContent(this)
}

internal class NetsurvLiveVideoStreamEntityStateComponentFactory(
    private val entityStateViewModelFactory: NetsurvLiveVideoStreamEntityStateViewModelFactory,
) :
    EntityStateComponentFactory<NetsurvLiveVideoStreamEntityState> {
    override val entityStateType: KClass<NetsurvLiveVideoStreamEntityState> = NetsurvLiveVideoStreamEntityState::class

    override fun create(
        state: StateFlow<Entity<NetsurvLiveVideoStreamEntityState>>,
        context: ComponentContext
    ): EntityStateComponent<NetsurvLiveVideoStreamEntityState> {
        return NetsurvLiveVideoStreamEntityStateComponent(entityStateViewModelFactory, state, context)
    }
}
