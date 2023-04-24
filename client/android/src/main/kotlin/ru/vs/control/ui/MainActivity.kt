package ru.vs.control.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import ru.vs.control.HelloView

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloView()
        }
    }
}
