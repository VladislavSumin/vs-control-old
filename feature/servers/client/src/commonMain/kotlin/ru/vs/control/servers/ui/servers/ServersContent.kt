package ru.vs.control.servers.ui.servers

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ServersContent(component: ServersComponent) {
    val state by component.state.collectAsState()
    Servers()
}

@Composable
private fun Servers() {
    Scaffold(
        floatingActionButton = { AddServer() }
    ) {
        LazyColumn(Modifier.padding(it)) {
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
            item { Server() }
        }
    }
}

@Composable
private fun AddServer() {
    FloatingActionButton(onClick = {}) {
        Text("+")
    }
}

@Composable
private fun Server(modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text("Server")
        }
    }
}

@Composable
@Preview
private fun ServerPreview() {
    MaterialTheme {
        Server()
    }
}
