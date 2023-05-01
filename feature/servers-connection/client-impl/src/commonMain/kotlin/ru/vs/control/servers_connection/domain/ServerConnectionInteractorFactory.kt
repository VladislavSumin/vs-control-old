package ru.vs.control.servers_connection.domain

import io.ktor.client.HttpClient
import ru.vs.control.servers.domain.Server

internal interface ServerConnectionInteractorFactory {
    fun create(server: Server): ServerConnectionInteractor
}

internal class ServerConnectionInteractorFactoryImpl(
    private val httpClient: HttpClient,
) : ServerConnectionInteractorFactory {
    override fun create(server: Server): ServerConnectionInteractor {
        return ServerConnectionInteractorImpl(httpClient, server)
    }
}
