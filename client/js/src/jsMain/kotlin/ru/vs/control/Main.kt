package ru.vs.control

import androidx.compose.ui.window.Window
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.lifecycle.stop
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady
import org.kodein.di.DI
import org.w3c.dom.Document
import ru.vs.control.ui.root.DefaultRootComponent
import ru.vs.control.ui.root.RootContent
import ru.vs.core.decompose.DiComponentContext
import ru.vs.core.di.Modules

fun main() {
    val di = DI.lazy {
        importOnce(Modules.clientCommon())
    }
    val lifecycle = LifecycleRegistry()
    val defaultContext = DefaultComponentContext(lifecycle)
    val defaultDiContext = DiComponentContext(defaultContext, di)
    val rootComponent = DefaultRootComponent(defaultDiContext)

    lifecycle.attachToDocument()

    onWasmReady {
        Window("Control") {
            RootContent(rootComponent)
        }
    }
}

// See https://arkivanov.github.io/Decompose/getting-started/quick-start/
private fun LifecycleRegistry.attachToDocument() {
    fun onVisibilityChanged() {
        if (document.visibilityState == "visible") resume() else stop()
    }

    onVisibilityChanged()

    document.addEventListener(type = "visibilitychange", callback = { onVisibilityChanged() })
}

private val Document.visibilityState: String
    get() = asDynamic().visibilityState.unsafeCast<String>()
