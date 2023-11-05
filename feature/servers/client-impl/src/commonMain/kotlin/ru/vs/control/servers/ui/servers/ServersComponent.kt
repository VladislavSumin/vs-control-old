package ru.vs.control.servers.ui.servers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import ru.vs.control.servers.domain.ServerId
import ru.vs.control.servers.ui.server_card.ServerCardComponentFactory
import ru.vs.core.decompose.ComposeComponent
import ru.vs.core.decompose.asValue
import ru.vs.core.decompose.createCoroutineScope
import ru.vs.core.decompose.router.list.childList
import ru.vs.core.factory_generator.GenerateFactory

@GenerateFactory(ServersComponentFactory::class)
internal class ServersComponent(
    private val serverCardComponentFactory: ServerCardComponentFactory,
    private val serversViewModelFactory: ServersViewModelFactory,
    private val openAddServerScreen: () -> Unit,
    private val openEditServerScreen: (ServerId) -> Unit,
    context: ComponentContext,
) : ComposeComponent, ComponentContext by context {
    private val scope = lifecycle.createCoroutineScope()
    private val viewModel: ServersViewModel = instanceKeeper.getOrCreate { serversViewModelFactory.create() }

    val serversList = childList(
        state = viewModel.state.asValue(scope),
        childFactory = { server, context ->
            serverCardComponentFactory.create(
                server,
                openEditServerScreen,
                context,
            )
        }
    )

    fun onClickAddServer() = openAddServerScreen()

    @Composable
    override fun Render(modifier: Modifier) = ServersContent(this)
}
