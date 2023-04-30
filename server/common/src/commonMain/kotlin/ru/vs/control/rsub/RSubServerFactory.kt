package ru.vs.control.rsub

import ru.vs.control.about_server.rsub.AboutServerRSub
import ru.vs.rsub.RSubServer

internal interface RSubServerFactory {
    fun create(): RSubServer
}

internal class RSubServerFactoryImpl(
    private val aboutServerRSub: AboutServerRSub,
) : RSubServerFactory {
    override fun create(): RSubServer {
        return RSubServer(createSubscriptions())
    }

    private fun createSubscriptions(): RSubSubscriptionsImpl {
        return RSubSubscriptionsImpl(
            aboutServerRSub,
        )
    }
}
