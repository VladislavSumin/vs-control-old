package ru.vs.control.service_cams_netsurv.domain

import ru.vs.control.id.Id
import ru.vs.control.services.domain.BaseService
import ru.vs.control.services.domain.Service

internal interface NetsurvCamsService : Service

internal class NetsurvCamsServiceImpl : BaseService(), NetsurvCamsService {
    override val id: Id = Id("cams/netsurv")
}
