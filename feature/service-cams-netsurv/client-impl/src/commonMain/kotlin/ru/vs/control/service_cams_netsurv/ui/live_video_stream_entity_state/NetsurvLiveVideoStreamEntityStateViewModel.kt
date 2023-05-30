package ru.vs.control.service_cams_netsurv.ui.live_video_stream_entity_state

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.servers_connection.domain.ServersConnectionInteractor
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvLiveVideoStreamEntityState
import ru.vs.core.decompose.viewmodel.ViewModel

internal class NetsurvLiveVideoStreamEntityStateViewModel(
    private val serversConnectionInteractor: ServersConnectionInteractor,
    val state: StateFlow<Entity<NetsurvLiveVideoStreamEntityState>>
) : ViewModel() {
    val liveVideoStream = flow { emit(serversConnectionInteractor.getConnection(state.value.server).netsurvCams) }
        .flatMapLatest { it.observeVideoLiveStream(state.value.primaryState.cameraId) }
}

internal class NetsurvLiveVideoStreamEntityStateViewModelFactory(
    private val serversConnectionInteractor: ServersConnectionInteractor,
) {
    fun create(state: StateFlow<Entity<NetsurvLiveVideoStreamEntityState>>): NetsurvLiveVideoStreamEntityStateViewModel {
        return NetsurvLiveVideoStreamEntityStateViewModel(serversConnectionInteractor, state)
    }
}
