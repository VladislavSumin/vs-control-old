package ru.vs.control.entities.ui.entities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.entities.domain.Entity

@Composable
internal fun EntitiesContent(component: EntitiesComponent) {
    val state by component.state.collectAsState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        item { Text("Entities screen") }
        items(state.entities) { entity ->
            EntityContent(entity, Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun EntityContent(entity: Entity, modifier: Modifier) {
    Card(modifier) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(entity.id.rawId)
            Text(entity.primaryState.toString())
        }
    }
}
