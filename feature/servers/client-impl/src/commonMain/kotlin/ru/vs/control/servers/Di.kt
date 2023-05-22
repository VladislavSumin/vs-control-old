package ru.vs.control.servers

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.servers.domain.ServersInteractor
import ru.vs.control.servers.domain.ServersInteractorImpl
import ru.vs.control.servers.repository.SelectedServerRepository
import ru.vs.control.servers.repository.SelectedServerRepositoryImpl
import ru.vs.control.servers.repository.ServersRepository
import ru.vs.control.servers.repository.ServersRepositoryImpl
import ru.vs.control.servers.ui.edit_server.EditServerComponentFactory
import ru.vs.control.servers.ui.edit_server.EditServerComponentFactoryImpl
import ru.vs.control.servers.ui.edit_server.EditServerStoreFactory
import ru.vs.control.servers.ui.server_card.ServerCardStoreFactory
import ru.vs.control.servers.ui.servers.ServerStoreFactory
import ru.vs.control.servers.ui.servers.ServersComponentFactory
import ru.vs.control.servers.ui.servers.ServersComponentFactoryImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureServers() = DI.Module("feature-servers") {
    bindSingleton<SelectedServerRepository> { SelectedServerRepositoryImpl(i()) }
    bindSingleton<ServersRepository> { ServersRepositoryImpl(i()) }

    bindSingleton<ServersInteractor> { ServersInteractorImpl(i(), i()) }

    bindSingleton { ServerStoreFactory(i(), i()) }
    bindSingleton { ServerCardStoreFactory(i(), i(), i()) }
    bindSingleton { EditServerStoreFactory(i(), i()) }

    bindSingleton<EditServerComponentFactory> { EditServerComponentFactoryImpl() }
    bindSingleton<ServersComponentFactory> { ServersComponentFactoryImpl() }
}
