package ru.vs.control.services.entity_states

import kotlinx.serialization.Serializable
import ru.vs.control.entities.domain.CompositeEntityState

/**
 * Represent service instance description entity state
 */
interface ServiceDescriptionCompositeEntityState : CompositeEntityState

/**
 * State for service description without dynamic content
 */
@Serializable
data object SimpleServiceDescriptionCompositeEntityState : ServiceDescriptionCompositeEntityState
