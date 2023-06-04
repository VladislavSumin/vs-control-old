package ru.vs.control.service_cams_netsurv.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.vs.control.service_cams_netsurv.domain.NetsurvCamera

internal interface NetsurvCamsRepository {
    fun observeCams(): Flow<List<NetsurvCamera>>
    suspend fun findCamera(cameraId: String): NetsurvCamera?
}

internal class NetsurvCamsRepositoryImpl : NetsurvCamsRepository {
    private val cams = listOf(
        NetsurvCamera(
            baseId = "lift",
            hostname = "10.10.2.2",
            port = 34567,
        ),
        NetsurvCamera(
            baseId = "dacha1",
            hostname = "10.10.5.2",
            port = 34567,
        ),
        NetsurvCamera(
            baseId = "dacha2",
            hostname = "10.10.5.3",
            port = 34567,
        ),
        NetsurvCamera(
            baseId = "dacha3",
            hostname = "10.10.5.4",
            port = 34567,
        ),
        NetsurvCamera(
            baseId = "dacha4",
            hostname = "10.10.5.5",
            port = 34567,
        ),
    )

    override fun observeCams(): Flow<List<NetsurvCamera>> {
        return flowOf(cams)
    }

    override suspend fun findCamera(cameraId: String): NetsurvCamera? {
        return cams.find { it.baseId == cameraId }
    }
}
