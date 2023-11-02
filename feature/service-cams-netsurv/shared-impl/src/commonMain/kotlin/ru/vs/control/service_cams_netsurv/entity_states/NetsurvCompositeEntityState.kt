package ru.vs.control.service_cams_netsurv.entity_states

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.EntityId
import ru.vs.control.entities.domain.EntityState

/**
 * Declare composite card with render all info about netsurv camera as one card
 * @param connectionId - reference to entity holds connection status
 * @param motionId - reference to entity holds motion status
 * @param liveVideoStreamId - reference to entity holds video live stream
 */
@Serializable
data class NetsurvCompositeEntityState(
    val connectionId: EntityId,
    val motionId: EntityId,
    val liveVideoStreamId: EntityId
) : EntityState
