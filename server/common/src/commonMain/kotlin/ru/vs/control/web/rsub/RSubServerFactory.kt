package ru.vs.control.web.rsub

import ru.vs.rsub.RSubServer

internal interface RSubServerFactory {
    fun create(): RSubServer
}

internal class RSubServerFactoryImpl : RSubServerFactory {
    override fun create(): RSubServer {
        return RSubServer(createSubscriptions())
    }

    private fun createSubscriptions(): RSubSubscriptionsImpl {
        return RSubSubscriptionsImpl()
    }
}