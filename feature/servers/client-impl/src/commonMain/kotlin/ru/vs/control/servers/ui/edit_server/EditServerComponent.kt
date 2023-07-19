package ru.vs.control.servers.ui.edit_server

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.launch
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(EditServerComponentFactory::class)
internal class EditServerComponent(
    editServerStoreFactory: EditServerStoreFactory,
    context: ComponentContext,
    serverId: Long?,
    closeScreen: () -> Unit,
) : ComposeComponent, ComponentContext by context {
    private val scope = lifecycle.createCoroutineScope()
    private val store: EditServerStore = instanceKeeper.getStore {
        editServerStoreFactory.create(serverId)
    }

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    EditServerStore.Label.CloseScreen -> closeScreen()
                }
            }
        }
    }

    val state = store.stateFlow
    fun accept(intent: EditServerStore.Intent) = store.accept(intent)

    @Composable
    override fun Render() = EditServerContent(this)
}
