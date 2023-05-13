package ru.vs.control.service_cams_netsurv.domain

import io.github.oshai.KotlinLogging
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

    private val connection = connectionFactory.create(camera.hostname, camera.port)

    override suspend fun run() {
        entitiesInteractor.holdEntity(
            Entity(
                id = CompositeId(NETSURV_CAMS_SERVICE_ID, Id("${camera.baseId}/connection_status")),
                primaryState = BooleanEntityState(false)
            )
        ) { update ->
            try {
                logger.debug { "run $camera" }
                connection.observeConnectionStatus().collect { isConnected ->
                    update {
                        it.copy(primaryState = BooleanEntityState(isConnected))
                    }
                }
            } finally {
                logger.debug { "cancel run $camera" }
            }
        }
    }
}
