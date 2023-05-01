package ru.vs.control.servers.ui.servers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import ru.vs.control.servers.client_impl.MR
import ru.vs.control.servers.ui.servers.ServersStore.ServerUiItem
import ru.vs.control.servers_connection.domain.ServerConnectionInteractor
import ru.vs.core.uikit.dropdown_menu.DropdownMenu
import ru.vs.core.uikit.dropdown_menu.DropdownMenuItem

@Composable
internal fun ServersContent(component: ServersComponent) {
    when (val state = component.state.collectAsState().value) {
        is ServersStore.State.Loaded -> Servers(state, component)
        ServersStore.State.Loading -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Servers(state: ServersStore.State.Loaded, component: ServersComponent) {
    Scaffold(
        floatingActionButton = { AddServer(component) }
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(state.servers, key = { it.server.id }) { server ->
                Server(server, component, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun AddServer(component: ServersComponent) {
    FloatingActionButton(onClick = { component.onClickAddServer() }) {
        Text("+")
    }
}

@Composable
private fun Server(server: ServerUiItem, component: ServersComponent, modifier: Modifier = Modifier) {
    Card(modifier) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Column {
                ServerHeader(server, component)
                Divider(Modifier.fillMaxWidth())
                Spacer(Modifier.defaultMinSize(minHeight = 8.dp))
                Row {
                    Text(
                        stringResource(MR.strings.servers_content_connection_status),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(Modifier.defaultMinSize(minWidth = 4.dp))

                    // TODO add colors to theme
                    val statusColor = when (server.connectionStatus) {
                        ServerConnectionInteractor.ConnectionStatus.Connected -> Color.Green
                        ServerConnectionInteractor.ConnectionStatus.Connecting -> Color.Yellow
                        is ServerConnectionInteractor.ConnectionStatus.Reconnecting -> Color.Red
                    }

                    val statusText = when (server.connectionStatus) {
                        ServerConnectionInteractor.ConnectionStatus.Connected ->
                            stringResource(MR.strings.servers_content_connection_status_connected)

                        ServerConnectionInteractor.ConnectionStatus.Connecting ->
                            stringResource(MR.strings.servers_content_connection_status_connecting)

                        is ServerConnectionInteractor.ConnectionStatus.Reconnecting ->
                            stringResource(MR.strings.servers_content_connection_status_connection_error)
                    }

                    Text(
                        statusText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = statusColor,
                    )
                }
            }
        }
    }
}

@Composable
private fun ServerHeader(
    server: ServerUiItem,
    component: ServersComponent
) {
    Row {
        Icon(
            Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.size(width = 12.dp, height = 0.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                server.server.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
            Text(
                "${server.server.host}:${server.server.port}",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
            )
        }
        Spacer(
            Modifier
                .defaultMinSize(minWidth = 12.dp)
        )
        ServerDropDownMenu(server, component)
    }
}

@Composable
private fun ServerDropDownMenu(
    server: ServerUiItem,
    component: ServersComponent,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { isExpanded = true }
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            DropdownMenuItem(
                { Text(stringResource(MR.strings.servers_content_edit)) },
                { component.onClickEditServer(server.server.id); isExpanded = false }
            )
            DropdownMenuItem(
                { Text(stringResource(MR.strings.servers_content_delete)) },
                { component.onClickDeleteServer(server.server.id); isExpanded = false }
            )
        }
    }
}

// @Composable
// @Preview
// private fun ServerPreview() {
//    MaterialTheme {
//        Server(
//            Server(0, "Server name", "https://control.vs.com/"),
//            Modifier.padding(16.dp)
//        )
//    }
// }
