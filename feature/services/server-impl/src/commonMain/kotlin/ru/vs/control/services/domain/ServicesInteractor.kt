package ru.vs.control.services.domain

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.vs.control.id.Id

internal interface ServicesInteractor {
    suspend fun registerService(service: Service)
}

internal class ServicesInteractorImpl : ServicesInteractor {
    private val services = mutableMapOf<Id, Service>()
    private val lock = Mutex()

    override suspend fun registerService(service: Service) {
        lock.withLock {
            check(services[service.id] == null) { "Service with ${service.id} already exists" }
            services[service.id] = service
        }
    }
}
