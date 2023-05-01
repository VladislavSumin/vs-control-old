package ru.vs.control.servers_connection.domain

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal interface ServersConnectionInteractor {
    // TODO добавить сюда интеграцию с модулем servers
    suspend fun getConnection(url: String): ServerConnectionInteractor
}

internal class ServersConnectionInteractorImpl(
    private val serverConnectionInteractorFactory: ServerConnectionInteractorFactory,
) : ServersConnectionInteractor {
    private val lock = Mutex()
    private val instances = mutableMapOf<String, ServerConnectionInteractor>()

    // TODO добавить очистку кеша
    override suspend fun getConnection(url: String): ServerConnectionInteractor {
        return lock.withLock {
            if (instances.containsKey(url)) {
                instances[url]!!
            } else {
                val instance = serverConnectionInteractorFactory.create(url)
                instances[url] = instance
                instance
            }
        }
    }
}
