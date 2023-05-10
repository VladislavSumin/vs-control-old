package ru.vs.control.service_cams_netsurv.domain

import io.github.oshai.KotlinLogging
import kotlinx.coroutines.delay

internal interface NetsurvCameraInteractor {
    suspend fun run()
}

internal class NetsurvCameraInteractorImpl(private val camera: NetsurvCamera) : NetsurvCameraInteractor {
    private val logger = KotlinLogging.logger("NetsurvCameraInteractor")

    override suspend fun run() {
        try {
            logger.debug { "run $camera" }
            delay(Long.MAX_VALUE)
        } finally {
            logger.debug { "cancel run $camera" }
        }
    }
}
