package ru.vs.control.main_screen.ui.main_screen

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreenContent(component: MainScreenComponent) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    Scaffold {
        ModalNavigationDrawer(
            drawerContent = { MainScreenDrawerContent() },
            drawerState = drawerState,
        ) {
            MainScreenNavigation(component)
        }
    }
}

@Composable
private fun MainScreenNavigation(component: MainScreenComponent) {
    Children(
        stack = component.stack,
    ) {
        it.instance.Render()
    }
}

@Composable
private fun MainScreenDrawerContent() {
    Surface(
        Modifier
            .width(200.dp)
            .fillMaxHeight()
    ) {
        Text("Drawer content")
    }
}
