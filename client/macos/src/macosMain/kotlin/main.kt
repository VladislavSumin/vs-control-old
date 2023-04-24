import androidx.compose.ui.window.Window
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import ru.vs.control.HelloProvider
import ru.vs.control.HelloView

fun main() {
    NSApplication.sharedApplication()
    println(HelloProvider.getHello())
    Window("Control") {
        HelloView()
    }
    NSApp?.run()
}