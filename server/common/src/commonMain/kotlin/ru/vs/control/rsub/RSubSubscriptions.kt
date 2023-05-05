package ru.vs.control.rsub

import ru.vs.control.about_server.rsub.AboutServerRSub
import ru.vs.control.entities.rsub.EntitiesRsub
import ru.vs.rsub.RSubServerSubscriptions

@RSubServerSubscriptions(
    [
        AboutServerRSub::class,
        EntitiesRsub::class,
    ]
)
internal interface RSubSubscriptions
