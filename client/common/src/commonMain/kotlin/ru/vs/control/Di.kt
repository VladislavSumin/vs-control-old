package ru.vs.control

import org.kodein.di.DI
import ru.vs.control.servers.featureServers
import ru.vs.core.di.Modules
import ru.vs.core.mvi.coreMvi

fun Modules.clientCommon() = DI.Module("client-common") {
    importOnce(Modules.coreMvi())

    importOnce(Modules.featureServers())
}
