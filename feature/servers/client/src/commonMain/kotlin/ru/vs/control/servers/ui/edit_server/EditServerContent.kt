package ru.vs.control.servers.ui.edit_server

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.vs.control.servers.ui.edit_server.EditServerStore.Intent

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
            value = state.url,
            singleLine = true,
            onValueChange = { component.accept(Intent.UpdateUrl(it)) },
            label = { Text("Server Url") },
        )

        Button(onClick = { component.accept(Intent.Save) }) { Text("Save") }
    }
}
