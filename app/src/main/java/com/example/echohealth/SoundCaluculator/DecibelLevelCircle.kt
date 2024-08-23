package com.example.echohealth.SoundCaluculator

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DecibellevelCircle(
    level: Float,
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(Color(0xFF29E33C), Color(0xFFD8FF5F)),
    strokeWidth: Dp = 8.dp,
){
    Canvas(modifier = modifier) {
        val cansize = size
        val Widthx = strokeWidth.toPx()
        val radi = cansize.minDimension / 2 - Widthx / 2
        drawCircle(
            color = Color.Gray,
            radius = radi + Widthx / 2,
            center = center,
            style = Stroke(Widthx)
        )
        val Angle = 360f *level
        val gradientBrush = Brush.sweepGradient(
            colors = gradientColors,
            center = center
        )
        drawArc(
            brush = gradientBrush,
            startAngle = -90f,
            sweepAngle = Angle,
            useCenter = false,
            style = Stroke(Widthx)
        )
        val radiiofcap = Widthx / 2
        val startAngle = Math.toRadians(-90.0)
        val endAngle = Math.toRadians(-90.0 + Angle.toDouble())
        val capStart = Offset(
            x = center.x + (radi + Widthx / 2) * cos(startAngle).toFloat(),
            y = center.y + (radi+ Widthx / 2) * sin(startAngle).toFloat()
        )
        val capEnd = Offset(
            x = center.x + (radi + Widthx / 2) * cos(endAngle).toFloat(),
            y = center.y + (radi + Widthx / 2) * sin(endAngle).toFloat()
        )
        drawCircle(
            color = gradientColors.last(),
            radius = radiiofcap,
            center = capStart
        )
        drawCircle(
            color = gradientColors.first(),
            radius = radiiofcap,
            center = capEnd
        )
    }
}