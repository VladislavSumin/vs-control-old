package ru.vs.control.entities.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.vs.control.entities.dto.toEntity
import ru.vs.control.servers_connection.domain.ServersConnectionInteractor

internal interface EntitiesInteractor {
    fun observeEntities(): Flow<List<Entity>>
}

internal class EntitiesInteractorImpl(
    private val serversConnectionInteractor: ServersConnectionInteractor,
) : EntitiesInteractor {
    override fun observeEntities(): Flow<List<Entity>> {
        return serversConnectionInteractor.observeSelectedServerConnection()
            .map { connection -> connection?.entities }
            .flatMapLatest { entities ->
                entities?.observeEntities()?.map { it.toEntity() } ?: flowOf(emptyList())
            }
    }
}
