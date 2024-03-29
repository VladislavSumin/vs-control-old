package ru.vs.control

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import ru.vs.control.about_server.featureAboutServer
import ru.vs.control.entities.featureEntities
import ru.vs.control.main_screen.featureMainScreen
import ru.vs.control.root_navigation.featureRootNavigation
import ru.vs.control.servers.featureServers
import ru.vs.control.servers.service.ServerQueriesProvider
import ru.vs.control.servers_connection.featureServersConnection
import ru.vs.control.service.DatabaseService
import ru.vs.control.service_cams_netsurv.featureServiceCamsNetsurv
import ru.vs.control.services.featureServices
import ru.vs.control.ui.root.RootComponentFactory
import ru.vs.control.ui.root.RootComponentFactoryImpl
import ru.vs.core.database.coreDatabase
import ru.vs.core.di.Modules
import ru.vs.core.di.i
import ru.vs.core.key_value_storage.coreKeyValueStorage
import ru.vs.core.ktor_client.coreKtorClient
import ru.vs.core.serialization.json.coreSerializationJson
import kotlin.coroutines.EmptyCoroutineContext

fun Modules.clientCommon() = DI.Module("client-common") {
    importOnce(Modules.coreDatabase())
    importOnce(Modules.coreKeyValueStorage())
    importOnce(Modules.coreKtorClient())
    importOnce(Modules.coreSerializationJson())

    importOnce(Modules.featureAboutServer())
    importOnce(Modules.featureEntities())
    importOnce(Modules.featureMainScreen())
    importOnce(Modules.featureRootNavigation())
    importOnce(Modules.featureServers())
    importOnce(Modules.featureServersConnection())
    importOnce(Modules.featureServiceCamsNetsurv())
    importOnce(Modules.featureServices())

    bindSingleton { CoroutineScope(EmptyCoroutineContext) }

    bindSingleton { DatabaseService(i()) }
    bindProvider<ServerQueriesProvider> { i<DatabaseService>() }
    bindSingleton<RootComponentFactory> { RootComponentFactoryImpl(i()) }
}
