import androidx.compose.ui.window.Window
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import org.kodein.di.DI
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import ru.vs.control.clientCommon
import ru.vs.control.ui.root.DefaultRootComponent
import ru.vs.control.ui.root.RootContent
import ru.vs.core.decompose.DiComponentContext
import ru.vs.core.di.Modules

fun main() {
    NSApplication.sharedApplication()

    val di = DI.lazy {
        importOnce(Modules.clientCommon())
    }
    // TODO пока на шару делаю, но тут точно нужен нормальный lifecycle
    val lifecycle = LifecycleRegistry()
    val defaultContext = DefaultComponentContext(lifecycle)
    val defaultDiContext = DiComponentContext(defaultContext, di)
    val rootComponent = DefaultRootComponent(defaultDiContext)

    lifecycle.create()

    Window("Control") {
        RootContent(rootComponent)
    }
    NSApp?.run()
}
