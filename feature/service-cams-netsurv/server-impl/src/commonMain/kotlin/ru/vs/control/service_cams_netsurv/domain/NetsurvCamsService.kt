package ru.vs.control.service_cams_netsurv.domain

import io.github.oshai.KotlinLogging
import ru.vs.control.id.Id
import ru.vs.control.services.domain.BaseService
import ru.vs.control.services.domain.Service

interface NetsurvCamsService : Service

private const val ID = "cams/netsurv"

internal class NetsurvCamsServiceImpl : BaseService(Id(ID)), NetsurvCamsService {
    private val logger = KotlinLogging.logger("NetsurvCamsService")

    override suspend fun run() {
        // no op
    }
}
