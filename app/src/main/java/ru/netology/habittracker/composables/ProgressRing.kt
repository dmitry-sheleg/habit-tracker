package ru.netology.habittracker.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ProgressRing(
    progressPercent: Int,
    size: Int = 48,
    trackColor: Color = Color.LightGray,
    progressColor: Color = Color(0xFF2E7D32), // зелёный
    showPercent: Boolean = true,
    content: @Composable (() -> Unit)? = null
) {
    val px = size.dp
    Box(modifier = Modifier.size(px).padding(4.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = (size - 8).toFloat() / 2f
            val center = Offset(size.toFloat() / 2, size.toFloat() / 2)
            // track
            drawCircle(color = trackColor, radius = radius, style = Stroke(width = 4.toFloat(), cap = StrokeCap.Round))
            // progress
            val sweepAngle = (progressPercent * 360f) / 100f
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(4f, 4f),
                size = Size(size - 8f, size - 8f),
                style = Stroke(width = 4f, cap = StrokeCap.Round)
            )
        }
        if (showPercent) {
            Text(
                text = "$progressPercent%",
                color = Color.Black,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
        } else if (content != null) {
            content()
        }
    }
}
