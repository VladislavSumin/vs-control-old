package ru.vs.control.servers.ui.server_card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import ru.vs.control.about_server.domain.AboutServerInteractor
import ru.vs.control.servers.client_impl.MR
import ru.vs.core.uikit.dropdown_menu.DropdownMenu
import ru.vs.core.uikit.dropdown_menu.DropdownMenuItem

@Composable
internal fun ServerCardContent(component: ServerCardComponent) {
    val state by component.state.collectAsState()
    Server(state, component)
}

@Composable
private fun Server(server: ServerCardStore.State, component: ServerCardComponent, modifier: Modifier = Modifier) {
    Card(
        modifier.clickable { component.onClickSelectServer() }
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            ServerHeader(server, component)
            Divider(Modifier.fillMaxWidth())
            Spacer(Modifier.defaultMinSize(minHeight = 8.dp))
            ServerConnectionStatus(server)
            ServerServerInfo(server)
        }
    }
}

@Composable
private fun ServerHeader(
    server: ServerCardStore.State,
    component: ServerCardComponent
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
            Row {
                Text(
                    server.server.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                )
                if (server.isSelected) {
                    Spacer(Modifier.defaultMinSize(minWidth = 8.dp))

                    Text(
                        "Selected",
                        Modifier.align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Green,
                        maxLines = 1,
                    )
                }
            }
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
        ServerDropDownMenu(component)
    }
}

@Composable
private fun ServerDropDownMenu(
    component: ServerCardComponent,
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
                { component.onClickEditServer(); isExpanded = false }
            )
            DropdownMenuItem(
                { Text(stringResource(MR.strings.servers_content_delete)) },
                { component.onClickDeleteServer(); isExpanded = false }
            )
        }
    }
}

@Composable
private fun ServerConnectionStatus(server: ServerCardStore.State) {
    Row {
        Text(
            stringResource(MR.strings.servers_content_connection_status),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.defaultMinSize(minWidth = 4.dp))

        // TODO add colors to theme
        val statusColor = when (server.connectionStatus) {
            is AboutServerInteractor.ConnectionStatusWithServerInfo.Connected -> Color.Green
            AboutServerInteractor.ConnectionStatusWithServerInfo.Connecting -> Color.Yellow
            is AboutServerInteractor.ConnectionStatusWithServerInfo.FailedToGetServerInfo -> Color.Red
            is AboutServerInteractor.ConnectionStatusWithServerInfo.Reconnecting -> Color.Red
        }

        val statusText = when (server.connectionStatus) {
            is AboutServerInteractor.ConnectionStatusWithServerInfo.Connected ->
                stringResource(MR.strings.servers_content_connection_status_connected)

            AboutServerInteractor.ConnectionStatusWithServerInfo.Connecting ->
                stringResource(MR.strings.servers_content_connection_status_connecting)

            is AboutServerInteractor.ConnectionStatusWithServerInfo.Reconnecting ->
                stringResource(MR.strings.servers_content_connection_status_connection_error)

            is AboutServerInteractor.ConnectionStatusWithServerInfo.FailedToGetServerInfo ->
                stringResource(MR.strings.servers_content_connection_status_get_server_info_error)
        }

        Text(
            statusText,
            style = MaterialTheme.typography.bodyMedium,
            color = statusColor,
        )
    }
}

@Composable
private fun ServerServerInfo(server: ServerCardStore.State) {
    val version = (server.connectionStatus as? AboutServerInteractor.ConnectionStatusWithServerInfo.Connected)
        ?.serverInfo?.version
    Text(
        "Server version: $version",
        style = MaterialTheme.typography.bodyMedium,
    )
}