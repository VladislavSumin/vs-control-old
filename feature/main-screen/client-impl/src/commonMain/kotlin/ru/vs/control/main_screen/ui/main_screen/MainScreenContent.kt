package ru.vs.control.main_screen.ui.main_screen

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import ru.vs.core.uikit.local_configuration.Configuration
import ru.vs.core.uikit.local_configuration.LocalConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreenContent(component: MainScreenComponent) {
    Scaffold {
        MainScreenNavigationDrawer(component) {
            MainScreenNavigation(component)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenNavigationDrawer(component: MainScreenComponent, content: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    when (LocalConfiguration.current.screenWidthSize) {
        Configuration.ScreenSize.Medium,
        Configuration.ScreenSize.Small ->
            ModalNavigationDrawer(
                drawerContent = { ModalDrawerSheet { MainScreenDrawerContent(component) } },
                drawerState = drawerState,
                content = content
            )

        Configuration.ScreenSize.Big ->
            PermanentNavigationDrawer(
                drawerContent = { PermanentDrawerSheet { MainScreenDrawerContent(component) } },
                content = content
            )
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
