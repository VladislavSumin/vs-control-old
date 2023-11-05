package ru.vs.control.servers.ui.server_header

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.servers_connection.domain.ServerConnectionInteractor
import ru.vs.core.uikit.letter_avatar.LetterAvatar

@Composable
internal fun ServerHeaderContent(component: ServerHeaderComponent, modifier: Modifier = Modifier) {
    val state = component.state.collectAsState().value ?: return

    Column(modifier) {
        LetterAvatar(
            state.server.name.firstOrNull().toString(),
            Modifier
                .align(Alignment.CenterHorizontally)
                .size(56.dp)
        )
        Text(
            state.server.name,
            Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            state.server.host,
            Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium,
        )
        val text = when (state.connectionStatus) {
            ServerConnectionInteractor.ConnectionStatus.Connected -> "Connected"
            ServerConnectionInteractor.ConnectionStatus.Connecting -> "Connecting"
            is ServerConnectionInteractor.ConnectionStatus.Reconnecting -> "Err"
        }
        Text(
            text,
            Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
private fun ServerHeaderPreview() {
    MaterialTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ServerHeaderComponentPreview.PreviewType.entries.forEachIndexed { index, previewType ->
                ServerHeaderContent(ServerHeaderComponentPreview(previewType, index))
            }
        }
    }
}
