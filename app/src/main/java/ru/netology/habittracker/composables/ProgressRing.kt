package ru.netology.habittracker.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProgressRing(
    progress: Int,                // 0–100
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp,
    color: Color = Color.Green,
    backgroundColor: Color = Color.LightGray
) {
    val safeProgress = when {
        progress < 0 -> 0
        progress > 100 -> 100
        else -> progress
    }

    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawProgressRing(
                progress = safeProgress / 100f,
                sizePx = size.toPx(),
                strokeWidthPx = strokeWidth.toPx(),
                color = color,
                backgroundColor = backgroundColor
            )
        }
    }
}

private fun DrawScope.drawProgressRing(
    progress: Float,
    sizePx: Float,
    strokeWidthPx: Float,
    color: Color,
    backgroundColor: Color
) {
    // Радиус внутренней части дуги (чтобы толщина не вылезала за границы)
    val radius = (sizePx - strokeWidthPx) / 2f
    val center = Offset(x = sizePx / 2f, y = sizePx / 2f)
    val rect = Size(width = radius * 2, height = radius * 2)
    val topLeft = Offset(center.x - radius, center.y - radius)

    // 1. Рисуем серое фоновое кольцо (всегда 360°)
    drawArc(
        color = backgroundColor,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        topLeft = topLeft,
        size = rect,
        style = Stroke(width = strokeWidthPx)
    )

    // 2. Рисуем цветную дугу прогресса
    if (progress > 0f) {
        val sweepAngle = progress * 360f
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = rect,
            style = Stroke(
                width = strokeWidthPx,
                cap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        )
    }
}


@Preview
@Composable
fun ProgressRingPreview() {
    ProgressRing(progress = 90)
}
