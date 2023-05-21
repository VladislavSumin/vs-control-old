package ru.vs.control.servers.ui.edit_server

import androidx.compose.runtime.Composable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.launch
import org.kodein.di.direct
import org.kodein.di.instance
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.DiComponentContext
import ru.vs.core.decompose.createCoroutineScope

internal class EditServerComponent(
    context: DiComponentContext,
    serverId: Long?,
    closeScreen: () -> Unit,
) : ComposeComponent, DiComponentContext by context {
    private val scope = lifecycle.createCoroutineScope()
    private val store: EditServerStore = instanceKeeper.getStore {
        direct.instance<EditServerStoreFactory>().create(serverId)
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
