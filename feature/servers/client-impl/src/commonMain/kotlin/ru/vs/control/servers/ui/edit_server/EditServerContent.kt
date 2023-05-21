package ru.vs.control.servers.ui.edit_server

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import ru.vs.control.servers.client_impl.MR
import ru.vs.control.servers.ui.edit_server.EditServerStore.Intent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditServerContent(component: EditServerComponent) {
    val state by component.state.collectAsState()
    Scaffold(
        topBar = { EditServerTopBar(state, component) },
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = state.name,
                singleLine = true,
                onValueChange = { component.accept(Intent.UpdateName(it)) },
                label = { Text(stringResource(MR.strings.edit_server_server_name)) },
            )

            OutlinedTextField(
                value = state.host,
                singleLine = true,
                onValueChange = { component.accept(Intent.UpdateHost(it)) },
                label = { Text(stringResource(MR.strings.edit_server_server_hostname)) },
            )

            Button(onClick = { component.accept(Intent.Save) }) {
                Text(stringResource(MR.strings.edit_server_save))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditServerTopBar(state: EditServerStore.State, component: EditServerComponent) {
    val titleResource = if (state.isEdit) MR.strings.edit_server_edit_server
    else MR.strings.edit_server_add_server
    TopAppBar(
        title = { Text(stringResource(titleResource)) },
        navigationIcon = {
            IconButton(onClick = { component.accept(Intent.Back) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
    )
}
