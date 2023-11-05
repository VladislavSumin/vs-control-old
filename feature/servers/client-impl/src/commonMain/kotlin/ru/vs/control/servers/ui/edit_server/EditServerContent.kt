package ru.vs.control.servers.ui.edit_server

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.vs.control.servers.ui.edit_server.EditServerViewModel.Intent
import ru.vs.core.navigation.NavigationSupportTopAppBar

@Composable
internal fun EditServerContent(component: EditServerComponent) {
    val state by component.state.collectAsState()
    Scaffold(
        topBar = { EditServerTopBar(state) },
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = state.name,
                singleLine = true,
                onValueChange = { component.accept(Intent.UpdateName(it)) },
                // TODO подождать пока mokko-resources обновится
                // label = { Text(stringResource(MR.strings.edit_server_server_name)) },
                label = { Text("Имя сервера") },
            )

            OutlinedTextField(
                value = state.host,
                singleLine = true,
                onValueChange = { component.accept(Intent.UpdateHost(it)) },
                // TODO подождать пока mokko-resources обновится
                // label = { Text(stringResource(MR.strings.edit_server_server_hostname)) },
                label = { Text("Hostname сервера") },
            )

            Button(onClick = { component.accept(Intent.Save) }) {
                // TODO подождать пока mokko-resources обновится
                // Text(stringResource(MR.strings.edit_server_save))
                Text("Сохранить")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditServerTopBar(state: EditServerViewState) {
    // TODO подождать пока mokko-resources обновится
    // val titleResource = if (state.isEditMode) MR.strings.edit_server_edit_server
    // else MR.strings.edit_server_add_server
    val titleResource = if (state.isEditMode) "Редактировать сервер"
    else "Добавить сервер"
    NavigationSupportTopAppBar(
        title = { Text(titleResource) },
    )
}
