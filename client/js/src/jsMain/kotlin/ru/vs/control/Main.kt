package ru.vs.control

import androidx.compose.ui.window.Window
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    println(HelloProvider.getHello())
    onWasmReady {
        Window {
            HelloView()
        }
    }
}