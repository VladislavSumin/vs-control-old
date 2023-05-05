package ru.vs.control.servers_connection

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.servers_connection.domain.ServerConnectionInteractorFactory
import ru.vs.control.servers_connection.domain.ServerConnectionInteractorFactoryImpl
import ru.vs.control.servers_connection.domain.ServersConnectionInteractor
import ru.vs.control.servers_connection.domain.ServersConnectionInteractorImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureServersConnection() = DI.Module("feature-servers-connection") {
    bindSingleton<ServerConnectionInteractorFactory> { ServerConnectionInteractorFactoryImpl(i(), i()) }
    bindSingleton<ServersConnectionInteractor> { ServersConnectionInteractorImpl(i(), i()) }
}
