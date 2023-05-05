package ru.vs.control.servers_connection.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.vs.control.servers.domain.Server
import ru.vs.control.servers.domain.ServersInteractor

internal class ServersConnectionInteractorImpl(
    private val serverConnectionInteractorFactory: ServerConnectionInteractorFactory,
    private val serversInteractor: ServersInteractor,
) : ServersConnectionInteractor {
    private val lock = Mutex()
    private val instances = mutableMapOf<Server, ServerConnectionInteractor>()

    // TODO добавить очистку кеша
    override suspend fun getConnection(server: Server): ServerConnectionInteractor {
        return lock.withLock {
            if (instances.containsKey(server)) {
                instances[server]!!
            } else {
                val instance = serverConnectionInteractorFactory.create(server)
                instances[server] = instance
                instance
            }
        }
    }

    override fun observeSelectedServerConnection(): Flow<ServerConnectionInteractor?> {
        return serversInteractor.observeSelectedServer()
            .map { server ->
                if (server != null) getConnection(server) else null
            }
    }
}
