package ru.vs.control.service_cams_netsurv.domain

import io.github.oshai.KotlinLogging
import kotlinx.coroutines.flow.collect
import ru.vs.control.service_cams_netsurv.network.NetsurvCameraConnectionFactory

internal interface NetsurvCameraInteractor {
    suspend fun run()
}

internal class NetsurvCameraInteractorImpl(
    private val camera: NetsurvCamera,
    connectionFactory: NetsurvCameraConnectionFactory,
) : NetsurvCameraInteractor {
    private val logger = KotlinLogging.logger("NetsurvCameraInteractor")

    private val connection = connectionFactory.create(camera.hostname, camera.port)

    override suspend fun run() {
        try {
            logger.debug { "run $camera" }
            connection.observeConnectionStatus().collect()
        } finally {
            logger.debug { "cancel run $camera" }
        }
    }
}
