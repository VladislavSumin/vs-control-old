package ru.vs.control.servers.ui.servers

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vs.control.servers.domain.Server

@Composable
internal fun ServersContent(component: ServersComponent) {
    val state by component.state.collectAsState()
    when (val state = state) {
        is ServersStore.State.Loaded -> Servers(state, component::onClickAddServer)
        ServersStore.State.Loading -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Servers(state: ServersStore.State.Loaded, onClick: () -> Unit) {
    Scaffold(
        floatingActionButton = { AddServer(onClick) }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(state.servers, key = { it.id }) { server ->
                Server(server)
            }
        }
    }
}

@Composable
private fun AddServer(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Text("+")
    }
}

@Composable
private fun Server(server: Server, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(server.name)
            Text(server.url)
        }
    }
}

@Composable
@Preview
private fun ServerPreview() {
    MaterialTheme {
        Server(
            Server(0, "Server name", "https://control.vs.com")
        )
    }
}
