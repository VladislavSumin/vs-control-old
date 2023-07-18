package ru.vs.control.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.control.ui.root.RootComponentFactory
import ru.vs.control.ui.root.RootContent

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultContext = defaultComponentContext()
        val rootComponentFactory = di.direct.instance<RootComponentFactory>()
        val rootComponent = rootComponentFactory.create(defaultContext)

        setContent {
            RootContent(rootComponent)
        }
    }
}
