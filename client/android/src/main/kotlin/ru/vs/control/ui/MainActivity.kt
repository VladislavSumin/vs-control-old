package ru.vs.control.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import ru.vs.control.ui.root.DefaultRootComponent
import ru.vs.control.ui.root.RootContent
import ru.vs.core.decompose.DiComponentContext

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultContext = defaultComponentContext()
        val defaultDiContext = DiComponentContext(defaultContext, di)
        val rootComponent = DefaultRootComponent(defaultDiContext)

        setContent {
            RootContent(rootComponent)
        }
    }
}
