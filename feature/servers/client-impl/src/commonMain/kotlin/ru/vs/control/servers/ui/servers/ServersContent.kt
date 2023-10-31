package ru.vs.control.servers.ui.servers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
internal fun ServersContent(component: ServersComponent) {
    Servers(component)
}

@Composable
private fun Servers(component: ServersComponent) {
    val servers by component.serversList.subscribeAsState()
    Scaffold(
        floatingActionButton = { AddServer(component) }
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(servers) { it.Render() }
        }
    }
}

@Composable
private fun AddServer(component: ServersComponent) {
    FloatingActionButton(onClick = { component.onClickAddServer() }) {
        Text("+")
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
