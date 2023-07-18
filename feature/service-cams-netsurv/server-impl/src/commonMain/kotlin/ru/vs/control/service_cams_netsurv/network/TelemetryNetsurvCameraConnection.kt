package ru.vs.control.service_cams_netsurv.network

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.vs.control.service_cams_netsurv.protocol.CommandCode
import ru.vs.control.service_cams_netsurv.protocol.CommandRepository
import ru.vs.core.network.service.NetworkService

internal class TelemetryNetsurvCameraConnection(
    networkService: NetworkService,
    scope: CoroutineScope,
    hostname: String,
    port: Int,
    private val reconnectInterval: Long = 5_000L,
    private val cancelAlarmTimeout: Long = 5_000L,
) : BaseNetsurvCameraConnection(networkService, hostname, port, reconnectInterval) {

    private val sharedConnection = runWithAutoReconnect { sessionId, read, write ->
        write(CommandRepository.alarmStart(sessionId))
        check(read().messageId == CommandCode.GUARD_RSP)

        // False means no alarm
        send(false)

        var alarmJob: Job? = null
        fun launchCancelAlarmJob() {
            alarmJob?.cancel()
            alarmJob = launch {
                delay(cancelAlarmTimeout)
                logger.trace { "Cancelling alarm for $hostname:$port" }
                send(false)
            }
        }

        while (true) {
            val msg = read()
            check(msg.messageId == CommandCode.ALARM_REQ)
            logger.trace { "Alarming for $hostname:$port" }
            send(true)
            launchCancelAlarmJob()
        }
    }
        .shareIn(
            scope + CoroutineName("TelemetryNetsurvCameraConnection::sharedConnection"),
            SharingStarted.WhileSubscribed(
                stopTimeoutMillis = 0,
                replayExpirationMillis = 0
            ),
            1
        )

    fun observeConnectionStatus(): Flow<Boolean> {
        return sharedConnection.map {
            when (it) {
                is ConnectionState.Connected -> true
                ConnectionState.Connecting,
                is ConnectionState.Reconnecting -> false
            }
        }
    }

    fun observeMotionStatus(): Flow<Boolean> {
        return sharedConnection.map {
            when (it) {
                is ConnectionState.Connected -> it.state
                ConnectionState.Connecting,
                is ConnectionState.Reconnecting -> false
            }
        }
    }
}
