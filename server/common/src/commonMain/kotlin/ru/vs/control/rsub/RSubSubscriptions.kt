package ru.vs.control.rsub

import ru.vs.control.about_server.rsub.AboutServerRSub
import ru.vs.rsub.RSubServerSubscriptions

@RSubServerSubscriptions([AboutServerRSub::class])
internal interface RSubSubscriptions
