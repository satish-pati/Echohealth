import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@Composable
fun Wavegen(isinRecording: Boolean) {
    val noOfBars = 50
    val repeatTrans = rememberInfiniteTransition()
    val animatedHeights = (0 until noOfBars).map { i ->
        repeatTrans.animateFloat(
            initialValue = 0.2f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 300,
                    delayMillis = (i% 10) * 20,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val Width = size.width / (noOfBars * 2f)
            val maximumHeight = size.height
            animatedHeights.forEachIndexed { idx, animh ->
                val barHeight = if (isinRecording) {
                    maximumHeight * animh.value * 1.1f
                } else {
                    maximumHeight * 0.5f *1.1f
                }

                drawRect(
                    color = Color.White,
                    topLeft = androidx.compose.ui.geometry.Offset(
                        x = idx * 2 * Width,
                        y = (maximumHeight - barHeight) / 2
                    ),
                    size = androidx.compose.ui.geometry.Size(Width, barHeight)
                )
            }
        }
    }
}
