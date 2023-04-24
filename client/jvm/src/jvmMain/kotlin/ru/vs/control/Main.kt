package ru.vs.control

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main(args: Array<String>) {
    println(HelloProvider.getHello())

    application {
        Window(onCloseRequest = ::exitApplication) {
            HelloView()
        }
    }
}
