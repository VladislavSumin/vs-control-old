package ru.vs.control.service_cams_netsurv.ui.live_video_stream_entity_state

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponent
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponentFactory
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvLiveVideoStreamEntityState
import kotlin.reflect.KClass

internal class NetsurvLiveVideoStreamEntityStateComponentFactory :
    EntityStateComponentFactory<NetsurvLiveVideoStreamEntityState> {
    override val entityStateType: KClass<NetsurvLiveVideoStreamEntityState> = NetsurvLiveVideoStreamEntityState::class

    override fun create(
        state: StateFlow<Entity<NetsurvLiveVideoStreamEntityState>>,
        context: ComponentContext
    ): EntityStateComponent<NetsurvLiveVideoStreamEntityState> {
        return NetsurvLiveVideoStreamEntityStateComponent(state, context)
    }
}
