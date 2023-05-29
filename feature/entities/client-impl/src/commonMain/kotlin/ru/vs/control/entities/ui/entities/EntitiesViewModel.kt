package ru.vs.control.entities.ui.entities

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.vs.control.entities.domain.EntitiesInteractor
import ru.vs.core.decompose.viewmodel.ViewModel

internal class EntitiesViewModel(
    entitiesInteractor: EntitiesInteractor,
) : ViewModel() {
    val state = entitiesInteractor.observeEntities()
        .map { it.values.sortedBy { it.id.rawId } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}

internal class EntitiesViewModelFactory(private val entitiesInteractor: EntitiesInteractor) {
    fun create(): EntitiesViewModel {
        return EntitiesViewModel(entitiesInteractor)
    }
}
