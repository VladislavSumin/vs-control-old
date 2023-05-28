package ru.vs.control.service_cams_netsurv.rsub

import kotlinx.coroutines.flow.Flow
import ru.vs.rsub.RSubInterface

@RSubInterface
interface NetsurvCamsRsub {
    fun observeVideoLiveStream(/*cameraId: String*/): Flow<ByteArray>
}
