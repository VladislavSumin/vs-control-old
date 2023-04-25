package ru.vs.control.servers.ui.servers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ServersContent(component: ServersComponent) {
    Box(Modifier.fillMaxSize()) {
        Text("Servers Content", Modifier.align(Alignment.Center), color = Color.Red)
    }
}