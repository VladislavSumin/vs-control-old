package ru.vs.control.main_screen.ui.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import kotlinx.coroutines.launch

@Composable
internal fun MainScreenDrawerContent(
    component: MainScreenComponent,
    drawerState: DrawerState,
) {
    Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        component.serverHeaderComponent.Render(Modifier.fillMaxWidth())
        Divider()
        Body(component, drawerState)
    }
}

@Composable
private fun Body(
    component: MainScreenComponent,
    drawerState: DrawerState,
) {
    val scope = rememberCoroutineScope()
    val selectedElement by component.selectedDrawerElement.subscribeAsState()
    DrawerElement.values().forEach { drawerElement ->
        NavigationDrawerItem(
            label = { Text(drawerElement.titleRes) },
            selected = drawerElement == selectedElement,
            onClick = {
                component.onSelectDrawerElement(drawerElement)
                scope.launch { drawerState.close() }
            },
            Modifier.padding(vertical = 4.dp)
        )
    }
}

// TODO подождать пока mokko-resources обновится
// private val DrawerElement.titleRes: StringResource
//    get() = when (this) {
//        DrawerElement.Entities -> MR.strings.drawer_item_entities
//        DrawerElement.Services -> MR.strings.drawer_item_services
//        DrawerElement.Servers -> MR.strings.drawer_item_servers
//    }

private val DrawerElement.titleRes: String
    get() = when (this) {
        DrawerElement.Entities -> "Сущности"
        DrawerElement.Services -> "Сервисы"
        DrawerElement.Servers -> "Сервера"
    }
