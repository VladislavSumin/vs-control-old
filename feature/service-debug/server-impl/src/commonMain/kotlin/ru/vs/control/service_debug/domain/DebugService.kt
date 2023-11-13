package ru.vs.control.service_debug.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.control.entities.domain.EntityId
import ru.vs.control.entities.domain.EntityProperties
import ru.vs.control.entities.domain.base_entity_properties.DefaultNameEntityProperty
import ru.vs.control.entities.domain.base_entity_states.BooleanEntityState
import ru.vs.control.id.Id
import ru.vs.control.services.domain.BaseService
import ru.vs.control.services.domain.Service
import ru.vs.control.services.entity_states.SimpleServiceDescriptionCompositeEntityState

/**
 * Debug service adds some basic debug entities
 */
interface DebugService : Service

private val DEBUG_SERVICE_ID = Id.SimpleId("debug")
private const val FLIP_FLOP_INTERVAL = 1000L

internal class DebugServiceImpl(
    private val entitiesInteractor: EntitiesInteractor,
) : BaseService(DEBUG_SERVICE_ID), DebugService {
    override suspend fun run(): Unit = coroutineScope {
        processServiceDescription()
        processFlipFlopEntity()
    }

    /**
     * Process flip-flop boolean entity
     */
    private fun CoroutineScope.processFlipFlopEntity() = this.launch {
        entitiesInteractor.holdEntity(
            id = EntityId(DEBUG_SERVICE_ID, Id.SimpleId("flip_flop")),
            primaryState = BooleanEntityState(false),
            properties = EntityProperties(
                DefaultNameEntityProperty("[debug] Flip-Flop")
            ),
        ) { update ->
            while (true) {
                delay(FLIP_FLOP_INTERVAL)
                update { entity ->
                    BooleanEntityState(!entity.primaryState.value)
                }
            }
        }
    }

    /**
     * Process service info
     * TODO make provide service description as part of interface
     */
    private fun CoroutineScope.processServiceDescription() = this.launch {
        entitiesInteractor.holdConstantEntity(
            id = EntityId(DEBUG_SERVICE_ID, Id.SimpleId("service_description")),
            primaryState = SimpleServiceDescriptionCompositeEntityState,
            properties = EntityProperties(
                DefaultNameEntityProperty("Debug Service")
            ),
        )
    }
}
