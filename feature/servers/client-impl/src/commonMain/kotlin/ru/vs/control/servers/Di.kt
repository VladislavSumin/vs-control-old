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
import ru.vs.control.servers.ui.edit_server.EditServerViewModelFactory
import ru.vs.control.servers.ui.edit_server.EditServerViewModelFactoryImpl
import ru.vs.control.servers.ui.server_card.ServerCardComponentFactory
import ru.vs.control.servers.ui.server_card.ServerCardComponentFactoryImpl
import ru.vs.control.servers.ui.server_card.ServerCardViewModelFactory
import ru.vs.control.servers.ui.server_card.ServerCardViewModelFactoryImpl
import ru.vs.control.servers.ui.server_header.ServerHeaderComponentFactory
import ru.vs.control.servers.ui.server_header.ServerHeaderComponentFactoryImpl
import ru.vs.control.servers.ui.server_header.ServerHeaderViewModelFactory
import ru.vs.control.servers.ui.server_header.ServerHeaderViewModelFactoryImpl
import ru.vs.control.servers.ui.servers.ServersComponentFactory
import ru.vs.control.servers.ui.servers.ServersComponentFactoryImpl
import ru.vs.control.servers.ui.servers.ServersViewModelFactory
import ru.vs.control.servers.ui.servers.ServersViewModelFactoryImpl
import ru.vs.core.di.Modules
import ru.vs.core.di.i

fun Modules.featureServers() = DI.Module("feature-servers") {
    // Repositories
    bindSingleton<SelectedServerRepository> { SelectedServerRepositoryImpl(i()) }
    bindSingleton<ServersRepository> { ServersRepositoryImpl(i()) }

    // Interactors
    bindSingleton<ServersInteractor> { ServersInteractorImpl(i(), i()) }

    // View model factories
    bindSingleton<ServersViewModelFactory> { ServersViewModelFactoryImpl(i()) }
    bindSingleton<ServerCardViewModelFactory> { ServerCardViewModelFactoryImpl(i(), i()) }
    bindSingleton<EditServerViewModelFactory> { EditServerViewModelFactoryImpl(i()) }
    bindSingleton<ServerHeaderViewModelFactory> { ServerHeaderViewModelFactoryImpl(i(), i()) }

    // Component factories
    bindSingleton<EditServerComponentFactory> { EditServerComponentFactoryImpl(i()) }
    bindSingleton<ServersComponentFactory> { ServersComponentFactoryImpl(i(), i()) }
    bindSingleton<ServerCardComponentFactory> { ServerCardComponentFactoryImpl(i()) }
    bindSingleton<ServerHeaderComponentFactory> { ServerHeaderComponentFactoryImpl(i()) }
}
