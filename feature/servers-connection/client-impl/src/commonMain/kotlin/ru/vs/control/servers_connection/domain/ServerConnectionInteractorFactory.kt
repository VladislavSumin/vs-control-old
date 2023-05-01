package ru.vs.control.servers_connection.domain

internal interface ServerConnectionInteractorFactory {
    fun create(url: String): ServerConnectionInteractor
}

internal class ServerConnectionInteractorFactoryImpl : ServerConnectionInteractorFactory {
    override fun create(url: String): ServerConnectionInteractor {
        return ServerConnectionInteractorImpl()
    }
}