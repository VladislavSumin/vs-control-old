import androidx.compose.ui.window.Window
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import ru.vs.control.clientCommon
import ru.vs.control.ui.root.RootComponentFactory
import ru.vs.control.ui.root.RootContent
import ru.vs.core.di.Modules

fun main() {
    NSApplication.sharedApplication()

    val di = DI.lazy {
        importOnce(Modules.clientCommon())
    }
    // TODO пока на шару делаю, но тут точно нужен нормальный lifecycle
    val lifecycle = LifecycleRegistry()
    val defaultContext = DefaultComponentContext(lifecycle)
    val rootComponentFactory = di.direct.instance<RootComponentFactory>()
    val rootComponent = rootComponentFactory.create(defaultContext)

    lifecycle.create()

    Window("Control") {
        RootContent(rootComponent)
    }
    NSApp?.run()
}
