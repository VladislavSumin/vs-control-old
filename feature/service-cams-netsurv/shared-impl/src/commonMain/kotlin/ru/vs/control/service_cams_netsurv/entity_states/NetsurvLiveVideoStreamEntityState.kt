package ru.vs.control.service_cams_netsurv.entity_states

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.EntityState

/**
 * State with link to [cameraId].
 * Used to show live camera stream.
 */
@Serializable
data class NetsurvLiveVideoStreamEntityState(val cameraId: String) : EntityState
