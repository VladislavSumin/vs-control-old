package ru.vs.control.entities.ui.entities.unknown_entity_state

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.entities.ui.entities.entity_state.EntityStateComponent

internal class UnknownEntityStateComponent(val state: StateFlow<Entity<*>>, context: ComponentContext) :
    EntityStateComponent, ComponentContext by context {
    @Composable
    override fun Render() = UnknownEntityStateContent(this)
}
