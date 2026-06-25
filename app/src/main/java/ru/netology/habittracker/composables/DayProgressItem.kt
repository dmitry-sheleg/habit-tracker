package ru.netology.habittracker.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
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

    val todayColor = Color(0xFF2E7D32)
    val defaultTextColor = if (isToday) todayColor else Color.Gray
    val backgroundColor = if (isToday) Color.Transparent else Color.LightGray

    Surface(
        modifier = Modifier
            .width(56.dp)
            .clickable(
                enabled = isToday,
                interactionSource = interactionSource,
                indication = null,
                onClick = onToggle
            ),
        color = backgroundColor,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = daysOfWeek[dayIndex],
                fontSize = 12.sp,
                color = defaultTextColor,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Галочка как простой индикатор выполнения (без кольца)
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = todayColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}


