package ru.vs.control.servers_connection.domain

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.vs.control.servers.domain.Server

interface ServersConnectionInteractor {
    // TODO добавить сюда интеграцию с модулем servers
    suspend fun getConnection(server: Server): ServerConnectionInteractor
}

internal class ServersConnectionInteractorImpl(
    private val serverConnectionInteractorFactory: ServerConnectionInteractorFactory,
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
}
