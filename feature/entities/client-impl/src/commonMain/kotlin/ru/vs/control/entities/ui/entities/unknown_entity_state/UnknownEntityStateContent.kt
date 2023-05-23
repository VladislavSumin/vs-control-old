package ru.vs.control.entities.ui.entities.unknown_entity_state

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun UnknownEntityStateContent(component: UnknownEntityStateComponent) {
    val entity by component.state.collectAsState()
    Card(Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(entity.id.rawId)
            Text(entity.primaryState.toString())
        }
    }
}
