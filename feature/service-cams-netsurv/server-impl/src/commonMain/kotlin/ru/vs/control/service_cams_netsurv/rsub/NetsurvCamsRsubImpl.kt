package ru.vs.control.service_cams_netsurv.rsub

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import ru.vs.control.service_cams_netsurv.domain.NetsurvCamsInteractor

internal class NetsurvCamsRsubImpl(
    private val netsurvCamsInteractor: NetsurvCamsInteractor,
) : NetsurvCamsRsub {
    override fun observeVideoLiveStream(cameraId: String): Flow<ByteArray> {
        return flow { emit(netsurvCamsInteractor.findCamera(cameraId) ?: return@flow) }
            .flatMapLatest { it.videoStreamConnection.observeVideoStream() }
    }
}
