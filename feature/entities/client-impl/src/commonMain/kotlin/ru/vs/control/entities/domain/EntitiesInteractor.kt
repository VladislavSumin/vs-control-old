package ru.vs.control.entities.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.vs.control.entities.dto.EntityDto
import ru.vs.control.servers_connection.domain.ServersConnectionInteractor

internal interface EntitiesInteractor {
    // TODO return DTO is temporary solution.
    fun observeEntities(): Flow<List<EntityDto>>
}

internal class EntitiesInteractorImpl(
    private val serversConnectionInteractor: ServersConnectionInteractor,
) : EntitiesInteractor {
    override fun observeEntities(): Flow<List<EntityDto>> {
        return serversConnectionInteractor.observeSelectedServerConnection()
            .map { connection -> connection?.entities }
            .flatMapLatest { entities -> entities?.observeEntities() ?: flowOf(emptyList()) }
    }
}
