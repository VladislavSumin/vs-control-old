package ru.vs.control.service_debug.domain

import kotlinx.coroutines.delay
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.base_entity_states.BooleanEntityState
import ru.vs.control.id.CompositeId
import ru.vs.control.id.Id
import ru.vs.control.services.domain.BaseService
import ru.vs.control.services.domain.Service

interface DebugService : Service

private val DEBUG_SERVICE_ID = Id("debug")
private const val FLIP_FLOP_INTERVAL = 1000L

internal class DebugServiceImpl(
    private val entitiesInteractor: EntitiesInteractor,
) : BaseService(DEBUG_SERVICE_ID), DebugService {
    override suspend fun run() {
        entitiesInteractor.holdEntity(
            Entity(
                CompositeId(DEBUG_SERVICE_ID, Id("flip_flop")),
                BooleanEntityState(false)
            )
        ) { update ->
            while (true) {
                delay(FLIP_FLOP_INTERVAL)
                update { entity ->
                    entity.copy(primaryState = BooleanEntityState(!entity.primaryState.value))
                }
            }
        }
    }
}
