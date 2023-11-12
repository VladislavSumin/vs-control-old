package ru.vs.control.services.ui.add_service

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.vs.core.navigation.NavigationSupportTopAppBar

@Composable
internal fun AddServiceContent() {
    Scaffold(
        topBar = { TopBar() }
    ) {
        Box(Modifier.padding(it)) {
            Text("Add service content")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    NavigationSupportTopAppBar(
        title = { Text("Add service") }
    )
}