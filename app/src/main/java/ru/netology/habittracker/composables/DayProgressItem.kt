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
            .padding(horizontal = 2.dp)  // небольшой отступ между ячейками
            .clickable(
                enabled = true,            // ВАЖНО: кликать можно в любой день, не только сегодня
                interactionSource = interactionSource,
                indication = null,
                onClick = onToggle
            ),
        color = if (isToday) todayBg else defaultBg,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
        tonalElevation = if (isToday) 0.dp else 1.dp // лёгкий подъём у не-сегодня, чтобы сегодня выглядело «плоским»
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = daysOfWeek[dayIndex],
                fontSize = 10.sp,          // уменьшил шрифт, чтобы влезло название дня
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


//package ru.netology.habittracker.composables
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//val daysOfWeek = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
//
//@Composable
//fun DayProgressItem(
//    dayIndex: Int,
//    isCompleted: Boolean,
//    onToggle: () -> Unit,
//    isToday: Boolean
//) {
//    val interactionSource = remember { MutableInteractionSource() }
//
//    val todayColor = Color(0xFF2E7D32)
//    val defaultTextColor = if (isToday) todayColor else Color.Gray
//    val backgroundColor = if (isToday) Color.Transparent else Color.LightGray
//
//    Surface(
//        modifier = Modifier
//            .width(40.dp)
//            .clickable(
//                enabled = isToday,
//                interactionSource = interactionSource,
//                indication = null,
//                onClick = onToggle
//            ),
//        color = backgroundColor,
//        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Text(
//                text = daysOfWeek[dayIndex],
//                fontSize = 12.sp,
//                color = defaultTextColor,
//                fontWeight = FontWeight.Medium
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            if (isCompleted) {
//                Icon(
//                    imageVector = Icons.Default.Check,
//                    contentDescription = null,
//                    tint = todayColor,
//                    modifier = Modifier.size(20.dp)
//                )
//            }
//        }
//    }
//}


