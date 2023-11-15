package ru.vs.control

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.control.ui.root.RootComponentFactory
import ru.vs.control.ui.root.RootContent
import ru.vs.core.di.Modules
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    val di = DI.lazy {
        importOnce(Modules.clientCommon())
    }

    val lifecycle = LifecycleRegistry()

    val rootComponent = runOnUiThread {
        val defaultContext = DefaultComponentContext(lifecycle)
        val rootComponentFactory = di.direct.instance<RootComponentFactory>()
        rootComponentFactory.create(defaultContext)
    }

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Control"
        ) {
            RootContent(rootComponent)
        }
    }
}

/**
 * See https://arkivanov.github.io/Decompose/getting-started/quick-start/
 * See https://github.com/arkivanov/Decompose/blob/master/sample/app-desktop/src/jvmMain/kotlin/com/arkivanov/sample/app/Utils.kt
 */
@Suppress("TooGenericExceptionCaught")
internal fun <T> runOnUiThread(block: () -> T): T {
    if (SwingUtilities.isEventDispatchThread()) {
        return block()
    }

    var error: Throwable? = null
    var result: T? = null

    SwingUtilities.invokeAndWait {
        try {
            result = block()
        } catch (e: Throwable) {
            error = e
        }
    }

    error?.also { throw it }

    @Suppress("UNCHECKED_CAST")
    return result as T
}
