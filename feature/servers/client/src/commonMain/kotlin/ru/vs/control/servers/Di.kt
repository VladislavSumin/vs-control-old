package ru.vs.control.servers

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.control.servers.domain.ServersInteractorImpl
import ru.vs.control.servers.ui.servers.ServerStoreFactory
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureServers() = DI.Module("feature-servers") {
    bindSingleton<ServersInteractor> { ServersInteractorImpl() }
    bindSingleton { ServerStoreFactory(i(), i()) }
}
