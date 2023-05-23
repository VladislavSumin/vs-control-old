package ru.vs.control.entities.ui.entities.unknown_entity_state

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponent
import ru.vs.core.decompose.DiComponentContext

internal class UnknownEntityStateComponent(val state: StateFlow<Entity<*>>, context: DiComponentContext) :
    EntityStateComponent, DiComponentContext by context {
    @Composable
    override fun Render() = UnknownEntityStateContent(this)
}
