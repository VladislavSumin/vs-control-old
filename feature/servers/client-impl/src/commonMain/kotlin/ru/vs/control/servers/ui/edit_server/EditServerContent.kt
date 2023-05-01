package ru.vs.control.servers.ui.edit_server

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.vs.control.servers.ui.edit_server.EditServerStore.Intent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditServerContent(component: EditServerComponent) {
    val state by component.state.collectAsState()

    Column {
        OutlinedTextField(
            value = state.name,
            singleLine = true,
            onValueChange = { component.accept(Intent.UpdateName(it)) },
            label = { Text("Server Name") },
        )

        OutlinedTextField(
            value = state.host,
            singleLine = true,
            onValueChange = { component.accept(Intent.UpdateHost(it)) },
            label = { Text("Server Host") },
        )

        Button(onClick = { component.accept(Intent.Save) }) { Text("Save") }
    }
}
