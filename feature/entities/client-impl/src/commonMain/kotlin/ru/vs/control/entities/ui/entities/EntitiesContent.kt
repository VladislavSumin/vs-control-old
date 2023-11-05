package ru.vs.control.entities.ui.entities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
internal fun EntitiesContent(component: EntitiesComponent) {
    Scaffold {
        val state by component.entitiesList.subscribeAsState()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(it)
        ) {
            item { Text("Entities screen") }
            items(state) { child ->
                child.Render(Modifier)
            }
        }
    }
}
