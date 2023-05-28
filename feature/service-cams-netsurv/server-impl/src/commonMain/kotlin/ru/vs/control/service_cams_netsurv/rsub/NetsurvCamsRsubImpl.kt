package ru.vs.control.service_cams_netsurv.rsub

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class NetsurvCamsRsubImpl : NetsurvCamsRsub {
    override fun observeVideoLiveStream(cameraId: String): Flow<String> {
        return flowOf("Test param $cameraId")
    }
}
