package ru.vs.control.rsub

import kotlinx.serialization.json.Json
import ru.vs.control.about_server.rsub.AboutServerRSub
import ru.vs.control.entities.rsub.EntitiesRsub
import ru.vs.rsub.RSubServer

internal interface RSubServerFactory {
    fun create(): RSubServer
}

internal class RSubServerFactoryImpl(
    private val aboutServerRSub: AboutServerRSub,
    private val entitiesRsub: EntitiesRsub,
    private val json: Json,
) : RSubServerFactory {
    override fun create(): RSubServer {
        return RSubServer(
            rSubServerSubscriptions = createSubscriptions(),
            json = json,
        )
    }

    private fun createSubscriptions(): RSubSubscriptionsImpl {
        return RSubSubscriptionsImpl(
            aboutServerRSub,
            entitiesRsub,
        )
    }
}
