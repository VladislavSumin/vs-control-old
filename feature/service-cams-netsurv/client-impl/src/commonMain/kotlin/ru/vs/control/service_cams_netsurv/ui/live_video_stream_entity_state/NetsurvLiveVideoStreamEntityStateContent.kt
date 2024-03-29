package ru.vs.control.service_cams_netsurv.ui.live_video_stream_entity_state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.vs.core.uikit.video_player.VideoPlayer

private const val ASPECT_RATIO_16_9 = 16 / 9f

@Composable
internal fun NetsurvLiveVideoStreamEntityStateContent(component: NetsurvLiveVideoStreamEntityStateComponent) {
    VideoPlayer(
        source = component.viewModel.liveVideoStream,
        Modifier
            .fillMaxWidth()
            .sizeIn(maxWidth = 400.dp)
            .aspectRatio(ASPECT_RATIO_16_9)
            .background(Color.Gray)
    )
}
