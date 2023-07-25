package ru.vs.control.servers.ui.edit_server

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.launch
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(EditServerComponentFactory::class)
internal class EditServerComponent(
    editServerViewModelFactory: EditServerViewModelFactory,
    context: ComponentContext,
    serverId: Long?,
    closeScreen: () -> Unit,
) : ComposeComponent, ComponentContext by context {
    private val scope = lifecycle.createCoroutineScope()
    private val viewModel: EditServerViewModel = instanceKeeper.getOrCreate {
        editServerViewModelFactory.create(serverId)
    }

    init {
        scope.launch {
            for (event in viewModel.events) {
                when (event) {
                    EditServerViewModel.Event.CloseScreen -> closeScreen()
                }
            }
        }
    }

    val state = viewModel.stateFlow
    fun accept(intent: EditServerViewModel.Intent) = viewModel.accept(intent)

    @Composable
    override fun Render() = EditServerContent(this)
}
