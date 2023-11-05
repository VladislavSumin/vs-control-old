package ru.vs.control.main_screen.ui.main_screen

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import ru.vs.core.navigation.WithDrawerNavigation
import ru.vs.core.navigation.WithNoneNavigation
import ru.vs.core.uikit.local_configuration.Configuration
import ru.vs.core.uikit.local_configuration.LocalConfiguration

@Composable
internal fun MainScreenNavigationDrawer(component: MainScreenComponent, content: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    when (LocalConfiguration.current.screenWidthSize) {
        Configuration.ScreenSize.Medium,
        Configuration.ScreenSize.Small -> {
            WithDrawerNavigation(
                onToggleDrawer = {
                    scope.launch {
                        if (drawerState.isOpen) drawerState.close()
                        else drawerState.open()
                    }
                }
            ) {
                ModalNavigationDrawer(
                    drawerContent = { ModalDrawerSheet { MainScreenDrawerContent(component, drawerState) } },
                    drawerState = drawerState,
                    content = content
                )
            }
        }

        Configuration.ScreenSize.Big -> {
            WithNoneNavigation {
                PermanentNavigationDrawer(
                    drawerContent = { PermanentDrawerSheet { MainScreenDrawerContent(component, drawerState) } },
                    content = content
                )
            }
        }
    }
}
