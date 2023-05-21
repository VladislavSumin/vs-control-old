package ru.vs.control.entities.ui.entities

import androidx.compose.runtime.Composable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext

internal class EntitiesComponent(context: DiComponentContext) : ComposeComponent, DiComponentContext by context {
    private val store: EntitiesStore = instanceKeeper.getStore {
        direct.instance<EntitiesStoreFactory>().create()
    }

    val state = store.stateFlow

    @Composable
    override fun Render() = EntitiesContent(this)
}
