import androidx.compose.ui.window.Window
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import ru.vs.control.ui.root.DefaultRootComponent
import ru.vs.control.ui.root.RootContent

fun main() {
    NSApplication.sharedApplication()

    // TODO пока на шару делаю, но тут точно нужен нормальный lifecycle
    val lifecycle = LifecycleRegistry()
    val defaultContext = DefaultComponentContext(lifecycle)
    val rootComponent = DefaultRootComponent(defaultContext)

    lifecycle.create()

    Window("Control") {
        RootContent(rootComponent)
    }
    NSApp?.run()
}
