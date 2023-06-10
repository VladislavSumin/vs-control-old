package ru.vs.control.service_cams_netsurv.ui.composite_entity_state

import kotlinx.coroutines.flow.StateFlow
import ru.vs.control.entities.domain.Entity
import ru.vs.control.servers_connection.domain.ServersConnectionInteractor
import ru.vs.control.service_cams_netsurv.entity_states.NetsurvCompositeEntityState
import ru.vs.core.decompose.viewmodel.ViewModel

internal class NetsurvCompositeEntityStateViewModel(
    private val serversConnectionInteractor: ServersConnectionInteractor,
    val state: StateFlow<Entity<NetsurvCompositeEntityState>>
) : ViewModel()

internal class NetsurvCompositeEntityStateViewModelFactory(
    private val serversConnectionInteractor: ServersConnectionInteractor,
) {
    fun create(state: StateFlow<Entity<NetsurvCompositeEntityState>>): NetsurvCompositeEntityStateViewModel {
        return NetsurvCompositeEntityStateViewModel(serversConnectionInteractor, state)
    }
}
