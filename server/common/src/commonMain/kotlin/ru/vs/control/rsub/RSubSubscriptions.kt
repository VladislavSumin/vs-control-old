package ru.vs.control.rsub

import ru.vs.control.about_server.rsub.AboutServerRSub
import ru.vs.control.entities.rsub.EntitiesRsub
import ru.vs.control.service_cams_netsurv.rsub.NetsurvCamsRsub
import ru.vs.rsub.RSubServerSubscriptions

@RSubServerSubscriptions(
    [
        AboutServerRSub::class,
        EntitiesRsub::class,
        NetsurvCamsRsub::class,
    ]
)
internal interface RSubSubscriptions
