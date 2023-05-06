package ru.vs.control.entities.ui.entities

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
internal fun EntitiesContent(component: EntitiesComponent) {
    val state by component.state.collectAsState()
    LazyColumn {
        item { Text("Entities screen") }
        items(state.entities) { entity ->
            Text(entity.id.rawId)
        }
    }
}
