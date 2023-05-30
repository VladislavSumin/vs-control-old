package ru.vs.control.service_cams_netsurv.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vs.control.service_cams_netsurv.repository.NetsurvCamsRepository

internal interface NetsurvCamsInteractor {
    fun observeCams(): Flow<List<NetsurvCameraInteractor>>
}

internal class NetsurvCamsInteractorImpl(
    private val netsurvCamsRepository: NetsurvCamsRepository,
    private val netsurvCameraInteractorFactory: NetsurvCameraInteractorFactory,
) : NetsurvCamsInteractor {
    override fun observeCams(): Flow<List<NetsurvCameraInteractor>> {
        return netsurvCamsRepository.observeCams()
            .map { cams -> cams.map { netsurvCameraInteractorFactory.create(it) } }
    }
}
