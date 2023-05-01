package ru.vs.control.servers_connection.domain

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import ru.vs.control.servers.domain.Server

internal interface ServerConnectionInteractorFactory {
    fun create(server: Server): ServerConnectionInteractor
}

internal class ServerConnectionInteractorFactoryImpl(
    private val httpClient: HttpClient,
    private val json: Json,
) : ServerConnectionInteractorFactory {
    override fun create(server: Server): ServerConnectionInteractor {
        return ServerConnectionInteractorImpl(httpClient, json, server)
    }
}
