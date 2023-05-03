package ru.vs.control.servers.repository

import kotlinx.coroutines.flow.Flow
import ru.vs.control.servers.domain.ServerId
import ru.vs.core.key_value_storage.service.KeyValueStorageService

internal interface SelectedServerRepository {
    fun observeSelectedServer(): Flow<ServerId?>
    suspend fun setSelectedServer(serverId: ServerId?)
}

internal class SelectedServerRepositoryImpl(
    keyValueStorageService: KeyValueStorageService,
) : SelectedServerRepository {

    private val preferences = keyValueStorageService.createStorage("selected_server_repository")

    private val selectedServerFlow = preferences.getLongOrNullFlow(SELECTED_SERVER_KEY)

    override fun observeSelectedServer(): Flow<ServerId?> = selectedServerFlow

    override suspend fun setSelectedServer(serverId: ServerId?) {
        if (serverId == null) preferences.remove(SELECTED_SERVER_KEY)
        else preferences.putLong(SELECTED_SERVER_KEY, serverId)
    }

    companion object {
        private const val SELECTED_SERVER_KEY = "selected_server"
    }
}
