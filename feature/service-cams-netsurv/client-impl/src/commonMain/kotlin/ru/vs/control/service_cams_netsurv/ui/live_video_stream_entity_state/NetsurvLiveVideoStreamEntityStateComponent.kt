package ru.vs.control.service_cams_netsurv.ui.live_video_stream_entity_state

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.ui.entities.entity_state.BaseEntityStateComponent
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvLiveVideoStreamEntityState

internal class NetsurvLiveVideoStreamEntityStateComponent(
    state: StateFlow<Entity<NetsurvLiveVideoStreamEntityState>>,
    context: ComponentContext,
) : BaseEntityStateComponent<NetsurvLiveVideoStreamEntityState>(state, context) {
    override fun Render() = NetsurvLiveVideoStreamEntityStateContent()
}
