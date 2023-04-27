package ru.vs.control

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.vs.control.repository.DatabaseFactory
import ru.vs.control.repository.createDatabaseFactory
import ru.vs.control.servers.featureServers
import ru.vs.core.di.Modules
import ru.vs.core.mvi.coreMvi

fun Modules.clientCommon() = DI.Module("client-common") {
    importOnce(Modules.coreMvi())

    importOnce(Modules.featureServers())

    bindSingleton<DatabaseFactory> { createDatabaseFactory() }
    bindSingleton { instance<DatabaseFactory>().createDatabase() }
}
