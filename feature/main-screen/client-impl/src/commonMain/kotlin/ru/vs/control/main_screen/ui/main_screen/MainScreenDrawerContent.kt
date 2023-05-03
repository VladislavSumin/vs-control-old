package ru.vs.control.main_screen.ui.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreenDrawerContent() {
    Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Header(Modifier.fillMaxWidth())
        Divider()

        NavigationDrawerItem(
            label = { Text("Drawer content") },
            selected = true,
            onClick = {}
        )
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Column(modifier) {
        Icon(
            Icons.Default.AccountBox,
            contentDescription = null,
            Modifier
                .size(56.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            "Control",
            Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            "control.vs.ru:8080",
            Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
