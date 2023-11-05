package ru.vs.control.services.ui.services

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.navigation.NavigationSupportTopAppBar

@Composable
internal fun ServicesContent(component: ServicesComponent) {
    component // for suppress linter errors
    Scaffold(
        topBar = { TopBar() },
        floatingActionButton = { AddServiceButton() }
    ) {
        Box(Modifier.padding(it)) {
            Text("Services screen")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    NavigationSupportTopAppBar(
        title = { Text("Services") },
    )
}

@Composable
private fun AddServiceButton() {
    FloatingActionButton(
        onClick = {}
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add new service")
    }
}
