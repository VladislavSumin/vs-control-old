package ru.vs.control.service_cams_netsurv.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vs.control.service_cams_netsurv.repository.NetsurvCamsRepository

/**
 * Provides access to [NetsurvCameraInteractor]
 */
internal interface NetsurvCamsInteractor {
    fun observeCams(): Flow<List<NetsurvCameraInteractor>>
    suspend fun findCamera(cameraId: String): NetsurvCameraInteractor?
}

internal class NetsurvCamsInteractorImpl(
    private val netsurvCamsRepository: NetsurvCamsRepository,
    private val netsurvCameraInteractorFactory: NetsurvCameraInteractorFactory,
) : NetsurvCamsInteractor {
    // TODO тут нужно добавить кеширование и шаринг соединений
    override fun observeCams(): Flow<List<NetsurvCameraInteractor>> {
        return netsurvCamsRepository.observeCams()
            .map { cams -> cams.map { netsurvCameraInteractorFactory.create(it) } }
    }

    override suspend fun findCamera(cameraId: String): NetsurvCameraInteractor? {
        val camera = netsurvCamsRepository.findCamera(cameraId) ?: return null
        return netsurvCameraInteractorFactory.create(camera)
    }
}
