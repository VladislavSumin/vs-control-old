package ru.vs.control.servers_connection.domain

import kotlinx.coroutines.flow.Flow

interface ServerConnectionInteractor {
    fun observeConnectionStatus(): Flow<Boolean>
}
