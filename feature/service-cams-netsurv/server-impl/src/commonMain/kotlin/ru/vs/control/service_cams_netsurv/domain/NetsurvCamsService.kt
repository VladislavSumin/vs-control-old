package ru.vs.control.service_cams_netsurv.domain

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.EntityId
import ru.vs.control.entities.domain.base_entity_states.BooleanEntityState
import ru.vs.control.id.Id
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvCompositeEntityState
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvLiveVideoStreamEntityState
import ru.vs.control.services.domain.BaseService
import ru.vs.control.services.domain.Service

interface NetsurvCamsService : Service

internal val NETSURV_CAMS_SERVICE_ID = Id.SimpleId("cams/netsurv")

internal class NetsurvCamsServiceImpl(
    private val netsurvCamsInteractor: NetsurvCamsInteractor,
    private val entitiesInteractor: EntitiesInteractor,
) : BaseService(NETSURV_CAMS_SERVICE_ID), NetsurvCamsService {

    override suspend fun run() {
        netsurvCamsInteractor.observeCams()
            .collectLatest { interactors ->
                coroutineScope {
                    interactors.forEach { interactor ->
                        launch { CameraProcessor(interactor).run() }
                    }
                }
            }
    }

    private inner class CameraProcessor(private val netsurvCameraInteractor: NetsurvCameraInteractor) {
        val connectionStatusId = generateEntityId("connection_status")
        val motionStatusId = generateEntityId("motion_status")
        val liveVideoStreamId = generateEntityId("live_video_stream")
        val compositeId = generateEntityId("composite")

        suspend fun run() {
            coroutineScope {
                launch { runConnectionState() }
                launch { runMotionState() }
                launch { runLiveVideoStreamState() }
                launch { runCompositeCameraState() }
            }
        }

        private suspend fun runConnectionState() {
            entitiesInteractor.holdEntity(
                Entity(
                    id = connectionStatusId,
                    primaryState = BooleanEntityState(false)
                )
            ) { update ->
                netsurvCameraInteractor.telemetryConnection.observeConnectionStatus().collect { isConnected ->
                    update {
                        it.copy(primaryState = BooleanEntityState(isConnected))
                    }
                }
            }
        }

        private suspend fun runMotionState() {
            entitiesInteractor.holdEntity(
                Entity(
                    id = motionStatusId,
                    primaryState = BooleanEntityState(false)
                )
            ) { update ->
                netsurvCameraInteractor.telemetryConnection.observeMotionStatus().collect { isMotion ->
                    update {
                        it.copy(primaryState = BooleanEntityState(isMotion))
                    }
                }
            }
        }

        private suspend fun runLiveVideoStreamState() {
            entitiesInteractor.holdConstantEntity(
                Entity(
                    id = liveVideoStreamId,
                    primaryState = NetsurvLiveVideoStreamEntityState(netsurvCameraInteractor.camera.baseId)
                )
            )
        }

        private suspend fun runCompositeCameraState() {
            entitiesInteractor.holdConstantEntity(
                Entity(
                    id = compositeId,
                    primaryState = NetsurvCompositeEntityState(
                        connectionId = connectionStatusId,
                        motionId = motionStatusId,
                        liveVideoStreamId = liveVideoStreamId,
                    )
                )
            )
        }

        private fun generateEntityId(subId: String): EntityId {
            return Id.DoubleId(
                NETSURV_CAMS_SERVICE_ID,
                Id.SimpleId("${netsurvCameraInteractor.camera.baseId}/$subId")
            )
        }
    }
}
