import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import kotlinx.cinterop.*
import platform.Foundation.NSStringFromClass
import platform.UIKit.*
import ru.vs.control.ui.root.DefaultRootComponent
import ru.vs.control.ui.root.RootContent

fun main() {
    val args = emptyArray<String>()
    memScoped {
        val argc = args.size + 1
        val argv = (arrayOf("skikoApp") + args).map { it.cstr.ptr }.toCValues()
        autoreleasepool {
            UIApplicationMain(argc, argv, null, NSStringFromClass(SkikoAppDelegate))
        }
    }
}

class SkikoAppDelegate : UIResponder, UIApplicationDelegateProtocol {
    companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta

    @ObjCObjectBase.OverrideInit
    constructor() : super()

    private var _window: UIWindow? = null
    override fun window() = _window
    override fun setWindow(window: UIWindow?) {
        _window = window
    }

    override fun application(application: UIApplication, didFinishLaunchingWithOptions: Map<Any?, *>?): Boolean {
        // TODO пока на шару делаю, но тут точно нужен нормальный lifecycle
        val lifecycle = LifecycleRegistry()
        val defaultContext = DefaultComponentContext(lifecycle)
        val rootComponent = DefaultRootComponent(defaultContext)

        lifecycle.create()

        window = UIWindow(frame = UIScreen.mainScreen.bounds)
        window!!.rootViewController = ComposeUIViewController {
            RootContent(rootComponent)
        }
        window!!.makeKeyAndVisible()
        return true
    }
}
