package ru.vs.control.service_cams_netsurv.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.vs.control.service_cams_netsurv.domain.NetsurvCamera

internal interface NetsurvCamsRepository {
    fun observeCams(): Flow<List<NetsurvCamera>>
}

internal class NetsurvCamsRepositoryImpl : NetsurvCamsRepository {
    override fun observeCams(): Flow<List<NetsurvCamera>> {
        return flowOf(
            listOf(
                NetsurvCamera(
                    baseId = "test_camera",
                    hostname = "10.10.5.2",
                    port = 34567,
                )
            )
        )
    }
}
