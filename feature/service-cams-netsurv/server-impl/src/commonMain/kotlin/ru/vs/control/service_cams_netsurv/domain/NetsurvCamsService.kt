package ru.vs.control.service_cams_netsurv.domain

import io.github.oshai.KotlinLogging
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vs.control.id.Id
import ru.vs.control.service_cams_netsurv.repository.NetsurvCamsRepository
import ru.vs.control.services.domain.BaseService
import ru.vs.control.services.domain.Service

interface NetsurvCamsService : Service

private const val ID = "cams/netsurv"

internal class NetsurvCamsServiceImpl(
    private val netsurvCamsRepository: NetsurvCamsRepository,
    private val netsurvCameraInteractorFactory: NetsurvCameraInteractorFactory,
) : BaseService(Id(ID)), NetsurvCamsService {
    private val logger = KotlinLogging.logger("NetsurvCamsService")

    override suspend fun run() {
        netsurvCamsRepository.observeCams()
            .map { cams ->
                cams.map { netsurvCameraInteractorFactory.create(it) }
            }
            .collectLatest { interactors ->
                coroutineScope {
                    interactors.forEach { interactor ->
                        launch { interactor.run() }
                    }
                }
            }
    }
}
