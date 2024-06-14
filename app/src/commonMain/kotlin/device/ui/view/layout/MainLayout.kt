package device.ui.view.layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import device.ui.theme.isHorizontal
import device.ui.theme.rememberForeverScrollState


@Composable
fun MainLayout() {
    val haptic = LocalHapticFeedback.current

    val scrollState = rememberForeverScrollState("MainAppViewLayout")
    val innerScrollStateStart = rememberForeverScrollState("MainAppViewLayoutStart")
    val innerScrollStateEnd = rememberForeverScrollState("MainAppViewLayoutEnd")

    BoxWithConstraints(Modifier.fillMaxSize()) {
        if (isHorizontal()) {
            Row(Modifier.fillMaxSize()) {


                Column(

                ) {

                }
            }
        } else {

        }
    }
}
