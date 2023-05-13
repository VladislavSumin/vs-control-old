package ru.vs.control.service_cams_netsurv.domain

import io.github.oshai.KotlinLogging
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.base_entity_states.BooleanEntityState
import ru.vs.control.id.CompositeId
import ru.vs.control.id.Id
import ru.vs.control.service_cams_netsurv.network.NetsurvCameraConnectionFactory

internal interface NetsurvCameraInteractor {
    suspend fun run()
}

internal class NetsurvCameraInteractorImpl(
    private val camera: NetsurvCamera,
    private val entitiesInteractor: EntitiesInteractor,
    connectionFactory: NetsurvCameraConnectionFactory,
) : NetsurvCameraInteractor {
    private val logger = KotlinLogging.logger("NetsurvCameraInteractor")

    private val telemetryConnection = connectionFactory.createTelemetry(camera.hostname, camera.port)
    // private val videoStreamConnection = connectionFactory.createTelemetry(camera.hostname, camera.port)

    override suspend fun run() {
        coroutineScope {
            launch { runConnectionState() }
            launch { runMotionState() }
        }
    }

    private suspend fun runConnectionState() {
        entitiesInteractor.holdEntity(
            Entity(
                id = generateEntityId("connection_status"),
                primaryState = BooleanEntityState(false)
            )
        ) { update ->
            telemetryConnection.observeConnectionStatus().collect { isConnected ->
                update {
                    it.copy(primaryState = BooleanEntityState(isConnected))
                }
            }
        }
    }

    private suspend fun runMotionState() {
        entitiesInteractor.holdEntity(
            Entity(
                id = generateEntityId("motion_status"),
                primaryState = BooleanEntityState(false)
            )
        ) { update ->
            telemetryConnection.observeMotionStatus().collect { isMotion ->
                update {
                    it.copy(primaryState = BooleanEntityState(isMotion))
                }
            }
        }
    }

    private fun generateEntityId(subId: String): CompositeId {
        return CompositeId(NETSURV_CAMS_SERVICE_ID, Id("${camera.baseId}/$subId"))
    }
}
