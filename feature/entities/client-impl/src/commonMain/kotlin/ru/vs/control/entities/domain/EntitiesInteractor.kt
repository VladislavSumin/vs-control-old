package ru.vs.control.entities.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import ru.vs.control.entities.dto.toEntity
import ru.vs.control.id.CompositeId
import ru.vs.control.servers_connection.domain.ServersConnectionInteractor

internal interface EntitiesInteractor {
    fun observeEntities(): Flow<Entities<*>>
    fun observeEntity(entityId: CompositeId): Flow<Entity<*>?>
}

internal class EntitiesInteractorImpl(
    private val serversConnectionInteractor: ServersConnectionInteractor,
    scope: CoroutineScope,
) : EntitiesInteractor {

    /**
     * Holds connection with current selected server and keep actual entities state
     */
    private val entitiesFlow: SharedFlow<Entities<*>> =
        serversConnectionInteractor.observeSelectedServerConnection()
            .map { connection -> connection?.entities }
            .flatMapLatest { entities ->
                entities?.observeEntities()
                    ?.map { it.toEntity().associateBy(Entity<*>::id) }
                    ?: flowOf(emptyMap())
            }
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 0, replayExpirationMillis = 0),
                replay = 1
            )

    override fun observeEntities(): Flow<Entities<*>> {
        return entitiesFlow
    }

    override fun observeEntity(entityId: CompositeId): Flow<Entity<*>?> {
        return entitiesFlow.map { it[entityId] }
    }
}
