package ru.vs.control

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import ru.vs.control.servers.featureServers
import ru.vs.control.servers.service.ServerQueriesProvider
import ru.vs.control.service.DatabaseService
import ru.vs.core.database.coreDatabase
import ru.vs.core.ktor_client.coreKtorClient
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.mvi.coreMvi

fun Modules.clientCommon() = DI.Module("client-common") {
    importOnce(Modules.coreDatabase())
    importOnce(Modules.coreKtorClient())
    importOnce(Modules.coreMvi())

    importOnce(Modules.featureServers())

    bindSingleton { DatabaseService(i()) }
    bindProvider<ServerQueriesProvider> { i<DatabaseService>() }
}
