package ru.vs.control.services.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.vs.control.id.Id

/**
 * Interact with group of services
 */
interface ServicesInteractor {

    /**
     * Register new service instance
     * @param service instance of service
     */
    suspend fun registerService(service: Service)
}

internal class ServicesInteractorImpl(
    private val scope: CoroutineScope,
) : ServicesInteractor {
    private val services = mutableMapOf<Id, Service>()
    private val lock = Mutex()

    override suspend fun registerService(service: Service) {
        lock.withLock {
            check(services[service.id] == null) { "Service with ${service.id} already exists" }
            services[service.id] = service
        }

        // Launch service.run() at separate job inside global server scope
        scope.launch { service.run() }
    }
}
