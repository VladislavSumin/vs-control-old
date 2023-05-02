import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import kotlinx.cinterop.ObjCObjectBase
import kotlinx.cinterop.autoreleasepool
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCValues
import org.kodein.di.DI
import platform.Foundation.NSStringFromClass
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationDelegateProtocol
import platform.UIKit.UIApplicationDelegateProtocolMeta
import platform.UIKit.UIApplicationMain
import platform.UIKit.UIResponder
import platform.UIKit.UIResponderMeta
import platform.UIKit.UIScreen
import platform.UIKit.UIWindow
import ru.vs.control.clientCommon
import ru.vs.control.ui.root.RootComponent
import ru.vs.control.ui.root.RootContent
import ru.vs.core.decompose.DiComponentContext
import ru.vs.core.di.Modules

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
        val di = DI.lazy {
            importOnce(Modules.clientCommon())
        }
        // TODO пока на шару делаю, но тут точно нужен нормальный lifecycle
        val lifecycle = LifecycleRegistry()
        val defaultContext = DefaultComponentContext(lifecycle)
        val defaultDiContext = DiComponentContext(defaultContext, di)
        val rootComponent = RootComponent(defaultDiContext)

        lifecycle.create()

        window = UIWindow(frame = UIScreen.mainScreen.bounds)
        window!!.rootViewController = ComposeUIViewController {
            RootContent(rootComponent)
        }
        window!!.makeKeyAndVisible()
        return true
    }
}
