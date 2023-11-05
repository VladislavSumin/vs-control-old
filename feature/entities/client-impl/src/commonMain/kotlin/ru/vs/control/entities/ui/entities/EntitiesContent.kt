package ru.vs.control.entities.ui.entities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import ru.vs.core.navigation.NavigationSupportTopAppBar

@Composable
internal fun EntitiesContent(component: EntitiesComponent) {
    Scaffold(
        topBar = { TopBar() }
    ) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    NavigationSupportTopAppBar(
        title = { Text("Entities") },
    )
}
