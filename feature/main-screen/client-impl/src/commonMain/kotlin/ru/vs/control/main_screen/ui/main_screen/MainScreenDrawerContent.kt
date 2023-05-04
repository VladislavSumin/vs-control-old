package ru.vs.control.main_screen.ui.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import ru.vs.control.main_screen.client_impl.MR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreenDrawerContent(
    component: MainScreenComponent,
    drawerState: DrawerState,
) {
    Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Header(Modifier.fillMaxWidth())
        Divider()
        Body(component, drawerState)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Body(
    component: MainScreenComponent,
    drawerState: DrawerState,
) {
    val scope = rememberCoroutineScope()
    val selectedElement by component.selectedDrawerElement.subscribeAsState()
    DrawerElement.values().forEach { drawerElement ->
        NavigationDrawerItem(
            label = { Text(stringResource(drawerElement.titleRes)) },
            selected = drawerElement == selectedElement,
            onClick = {
                component.onSelectDrawerElement(drawerElement)
                scope.launch { drawerState.close() }
            },
            Modifier.padding(vertical = 4.dp)
        )
    }
}

private val DrawerElement.titleRes: StringResource
    get() = when (this) {
        DrawerElement.Entities -> MR.strings.drawer_item_entities
        DrawerElement.Servers -> MR.strings.drawer_item_servers
    }
