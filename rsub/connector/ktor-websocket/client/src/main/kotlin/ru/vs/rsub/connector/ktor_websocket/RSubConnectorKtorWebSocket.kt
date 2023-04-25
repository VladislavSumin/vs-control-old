package ru.vs.rsub.connector.ktor_websocket

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import ru.vs.rsub.RSubConnection
import ru.vs.rsub.RSubConnector
import ru.vs.rsub.RSubException
import ru.vs.rsub.RSubExpectedExceptionOnConnectionException
import java.net.SocketException
import java.net.SocketTimeoutException

class RSubConnectorKtorWebSocket(
    private val client: HttpClient,
    private val host: String = "localhost",
    private val path: String = "/rSub",
    private val port: Int = 8080
) : RSubConnector {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun connect(): RSubConnection {
        try {
            val session = client.webSocketSession(
                method = HttpMethod.Get,
                host = host,
                port = port,
                path = path,
            ) {
                setAttributes {
                    header(HttpHeaders.SecWebSocketProtocol, "rSub")
                }
            }
            return RSubConnectionKtorWebSocket(session)
        } catch (e: Exception) {
            throw when (e) {
                is SocketException,
                is SocketTimeoutException ->
                    RSubExpectedExceptionOnConnectionException("Catch checked exception while connect", e)
                else -> RSubException("Unknown exception while connect", e)
            }
        }
    }
}
