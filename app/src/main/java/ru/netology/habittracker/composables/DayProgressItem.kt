package ru.netology.habittracker.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val daysOfWeek = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

@Composable
fun DayProgressItem(
    dayIndex: Int,
    isCompleted: Boolean,
    onToggle: () -> Unit,
    isToday: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isClickable = isToday
    val background = if (isClickable) Color.Transparent else Color.LightGray

    Surface(
        modifier = Modifier
            .width(56.dp)
            .clickable(
                enabled = isClickable,
                interactionSource = interactionSource,
                indication = null, // убираем стандартный ripple, если нужно
                onClick = onToggle
            ),
        color = background,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = daysOfWeek[dayIndex],
                fontSize = 12.sp,
                color = if (isToday) Color(0xFF2E7D32) else Color.Gray
            )
            if (isCompleted) {
                ProgressRing(
                    progressPercent = 100,
                    size = 32,
                    trackColor = Color.Transparent,
                    progressColor = Color(0xFF2E7D32),
                    showPercent = false,
                    content = {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White)
                    }
                )
            } else {
                // пустой круг или серый индикатор
                ProgressRing(
                    progressPercent = 0,
                    size = 32,
                    trackColor = if (isClickable) Color.LightGray else Color.Gray,
                    progressColor = Color.Transparent,
                    showPercent = false
                )
            }
        }
    }
}
