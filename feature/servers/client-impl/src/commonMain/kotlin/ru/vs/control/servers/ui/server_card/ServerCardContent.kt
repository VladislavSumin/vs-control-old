package ru.vs.control.servers.ui.server_card

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
import ru.vs.control.about_server.domain.AboutServerInteractor
import ru.vs.core.uikit.dropdown_menu.DropdownMenu
import ru.vs.core.uikit.dropdown_menu.DropdownMenuItem
import ru.vs.core.uikit.letter_avatar.LetterAvatar

@Composable
internal fun ServerCardContent(component: ServerCardComponent) {
    val state by component.state.collectAsState()
    Server(state, component)
}

@Composable
private fun Server(server: ServerCardViewState, component: ServerCardComponent, modifier: Modifier = Modifier) {
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
    server: ServerCardViewState,
    component: ServerCardComponent
) {
    Row {
        LetterAvatar(
            server.server.name.firstOrNull().toString(),
            Modifier
                .align(Alignment.CenterVertically)
                .size(40.dp)
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
                {
                    // TODO подождать пока mokko-resources обновится
                    // Text(stringResource(MR.strings.servers_content_edit))
                    Text("Изменить")
                },
                {
                    component.onClickEditServer()
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                {
                    // TODO подождать пока mokko-resources обновится
                    // Text(stringResource(MR.strings.servers_content_delete))
                    Text("Удалить")
                },
                {
                    component.onClickDeleteServer()
                    isExpanded = false
                }
            )
        }
    }
}

@Composable
private fun ServerConnectionStatus(server: ServerCardViewState) {
    Row {
        Text(
            // TODO подождать пока mokko-resources обновится
            // stringResource(MR.strings.servers_content_connection_status),
            "Статус подключения:",
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

        // TODO подождать пока mokko-resources обновится
        val statusText = when (server.connectionStatus) {
            is AboutServerInteractor.ConnectionStatusWithServerInfo.Connected ->
                // stringResource(MR.strings.servers_content_connection_status_connected)
                "Подключено"

            AboutServerInteractor.ConnectionStatusWithServerInfo.Connecting ->
                // stringResource(MR.strings.servers_content_connection_status_connecting)
                "Подключение"

            is AboutServerInteractor.ConnectionStatusWithServerInfo.Reconnecting ->
                // stringResource(MR.strings.servers_content_connection_status_connection_error)
                "Ошибка подключения"

            is AboutServerInteractor.ConnectionStatusWithServerInfo.FailedToGetServerInfo ->
                // stringResource(MR.strings.servers_content_connection_status_get_server_info_error)
                "Ошибка при получении информации о сервере"
        }

        Text(
            statusText,
            style = MaterialTheme.typography.bodyMedium,
            color = statusColor,
        )
    }
}

@Composable
private fun ServerServerInfo(server: ServerCardViewState) {
    val version = (server.connectionStatus as? AboutServerInteractor.ConnectionStatusWithServerInfo.Connected)
        ?.serverInfo?.version
    Text(
        "Server version: $version",
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Preview
@Composable
private fun ServerCardPreview() {
    MaterialTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ServerCardComponentPreview.PreviewType.entries.forEachIndexed { index, previewType ->
                ServerCardContent(ServerCardComponentPreview(previewType, index))
            }
        }
    }
}
