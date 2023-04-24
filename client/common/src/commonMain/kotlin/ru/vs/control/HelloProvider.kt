package ru.vs.control

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

object HelloProvider {
    private const val HELLO_STRING = "Hello from common module"
    fun getHello(): String {
        return HELLO_STRING
    }
}

@Composable
fun HelloView() {
    Box(Modifier.fillMaxSize()) {
        Text("Hello compose", Modifier.align(Alignment.Center), color = Color.Red)
    }
}
