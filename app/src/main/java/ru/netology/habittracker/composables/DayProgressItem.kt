package ru.netology.habittracker.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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

    // Цвета
    val primaryColor = MaterialTheme.colorScheme.primary
    val defaultBg = Color.LightGray
    val todayBg = Color.Transparent

    Surface(
        modifier = Modifier
            .width(44.dp)
            .padding(horizontal = 2.dp)
            .clickable(
                enabled = true,
                interactionSource = interactionSource,
                indication = null,
                onClick = onToggle
            ),
        color = if (isToday) todayBg else defaultBg,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
        tonalElevation = if (isToday) 0.dp else 1.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = daysOfWeek[dayIndex],
                fontSize = 10.sp,
                color = if (isToday) primaryColor else Color.Gray,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(2.dp))

            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}