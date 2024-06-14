package device.ui.theme

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay


val bodyTextFontSize: TextUnit = 16.sp
val headTextFontSize: TextUnit = 22.sp
val footTextFontSize: TextUnit = 12.sp
val statusTextFontSize: TextUnit = 26.sp

val horizontalLayoutThreshold = 600.dp
@Composable
fun BoxWithConstraintsScope.isHorizontal(): Boolean = this.maxWidth > horizontalLayoutThreshold

val dialogOpeningHaptic: suspend CoroutineScope.((HapticFeedbackType) -> Unit, suspend CoroutineScope.() -> Unit) -> Unit
        = { performHapticFeedback, openDial ->
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(30)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(30)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(30)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(30)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(30)
    performHapticFeedback(HapticFeedbackType.LongPress)
    openDial()
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(100)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(50)
    performHapticFeedback(HapticFeedbackType.LongPress)
}

val textAnimationHaptic: suspend CoroutineScope.((HapticFeedbackType) -> Unit) -> Unit = { performHapticFeedback ->
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(200)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
    delay(10)
    performHapticFeedback(HapticFeedbackType.LongPress)
}


/**
 * Static field, contains all scroll values
 */
private val SaveMap = mutableMapOf<String, ScrollKeyParams>()

private data class ScrollKeyParams(
    val value: Int
)

/**
 * Save scroll state on all time.
 * @param key value for comparing screen
 * @param initial see [ScrollState.value]
 * @see <a href="https://stackoverflow.com/questions/68611320/remember-lazycolumn-scroll-position-jetpack-compose">Save ScrollState</a>
 */
@Composable
fun rememberForeverScrollState(
    key: String,
    initial: Int = 0
): ScrollState {
    val scrollState = rememberSaveable(saver = ScrollState.Saver) {
        val scrollValue: Int = SaveMap[key]?.value ?: initial
        SaveMap[key] = ScrollKeyParams(scrollValue)
        return@rememberSaveable ScrollState(scrollValue)
    }
    DisposableEffect(Unit) {
        onDispose {
            SaveMap[key] = ScrollKeyParams(scrollState.value)
        }
    }
    return scrollState
}

