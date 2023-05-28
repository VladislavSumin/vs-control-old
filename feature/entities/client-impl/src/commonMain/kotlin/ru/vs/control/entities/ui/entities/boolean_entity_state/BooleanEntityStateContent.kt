package ru.vs.control.entities.ui.entities.boolean_entity_state

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
internal fun BooleanEntityStateContent(component: BooleanEntityStateComponent) {
    val entity by component.entityState.collectAsState()
    Card(Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(entity.id.rawId)
            Text("Boolean=${entity.primaryState.value}")
        }
    }
}