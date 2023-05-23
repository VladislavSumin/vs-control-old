package ru.vs.control.entities.ui.entities.entity_state

import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.domain.EntityState
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

/**
 * Base component for render [Entity] with some [EntityState]
 * To known how to register our own [EntityStateComponent] see [EntityStateComponentFactory] documentation
 */
interface EntityStateComponent : ComposeComponent, DiComponentContext
