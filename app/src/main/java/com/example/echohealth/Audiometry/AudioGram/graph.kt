package com.example.echohealth.Audiometry.AudioGram
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.example.echohealth.Audiometry.AudiometryData.AudiometryResult

@Composable
fun LineGraph(
    leftEarResults: List<AudiometryResult>,
    rightEarResults: List<AudiometryResult>
) {

    val frequencies = listOf(250, 500, 1000, 2000, 4000, 8000)
    val minFrequency = frequencies.first()
    val maxFrequency = frequencies.last()
    val minDecibels = 0
    val maxDecibels = 120
    val decibelInterval = 20

    fun mapToPoints(
        results: List<AudiometryResult>,
        graphWidth: Float,
        graphHeight: Float,
        padding: Float
    ): List<Offset> {
        return frequencies.map { freq ->
            val matchingResult = results.find { it.frequency == freq}
            val x = (freq- minFrequency).toFloat() / (maxFrequency - minFrequency) * graphWidth + padding
            val y = matchingResult?.let {
                graphHeight - (it.decibels - minDecibels).toFloat() / (maxDecibels - minDecibels) * graphHeight + padding
            } ?: (graphHeight + padding)
            Offset(x, y)
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val padding = 40.dp.toPx()
        val graphWidth = width - 2 * padding
        val graphHeight = height - 2 * padding

        val leftEarPoints = mapToPoints(leftEarResults, graphWidth, graphHeight, padding)
        val rightEarPoints = mapToPoints(rightEarResults, graphWidth, graphHeight, padding)
        if (leftEarPoints.isNotEmpty()) {
            drawPath(
                path = Path().apply {
                    moveTo(leftEarPoints.first().x, leftEarPoints.first().y)
                    leftEarPoints.forEach { point ->
                        lineTo(point.x, point.y)
                    }
                },
                color = Color.Blue,
                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        if (rightEarPoints.isNotEmpty()) {
            drawPath(
                path = Path().apply {
                    moveTo(rightEarPoints.first().x, rightEarPoints.first().y)
                    rightEarPoints.forEach { point ->
                        lineTo(point.x, point.y)
                    }
                },
                color = Color.Red,
                style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        leftEarPoints.forEach { point ->
            drawCircle(
                color = Color.Blue,
                radius = 6.dp.toPx(),
                center = point
            )
        }
        rightEarPoints.forEach { point ->
            drawCircle(
                color = Color.Red,
                radius = 6.dp.toPx(),
                center = point
            )
        }


        drawRect(
            color = Color.LightGray,
            size = Size(graphWidth, graphHeight),
            topLeft = Offset(padding, padding),
            style = Stroke(width = 1.dp.toPx())
        )


        val frequencyStep = graphWidth / (frequencies.size - 1).toFloat()
        frequencies.forEachIndexed { idx, freq ->
            val x = padding + idx * frequencyStep
            drawLine(
                color = Color.LightGray,
                start = Offset(x, padding),
                end = Offset(x, height - padding),
                strokeWidth = 0.5.dp.toPx()
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "$freq",
                    x,
                    height - padding / 2,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 30f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }


        for (i in minDecibels..maxDecibels step decibelInterval) {
            val y = padding + graphHeight - (i.toFloat() / maxDecibels * graphHeight)
            drawLine(
                color = Color.LightGray,
                start = Offset(padding, y),
                end = Offset(width - padding, y),
                strokeWidth = 0.5.dp.toPx()
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "$i",
                    padding / 2,
                    y,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 30f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}
