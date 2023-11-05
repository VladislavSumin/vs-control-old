package ru.vs.control.main_screen.ui.main_screen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@Composable
internal fun MainScreenContent(component: MainScreenComponent) {
    Scaffold {
        MainScreenNavigationDrawer(component) {
            MainScreenNavigation(component)
        }
    }
}

@Composable
private fun MainScreenNavigation(component: MainScreenComponent) {
    Children(
        stack = component.stack,
    ) {
        it.instance.Render(Modifier)
    }
}
