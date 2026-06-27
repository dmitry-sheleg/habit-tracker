package ru.netology.habittracker.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.netology.habittracker.dto.Habit
import ru.netology.habittracker.utils.getTodayDayIndex

@Composable
fun HabitCard(
    habit: Habit,
    onToggleDay: (Long, Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = habit.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {
                        repeat(7) { dayIndex ->
                            val isCompleted = if (dayIndex in habit.dailyStatus.indices) habit.dailyStatus[dayIndex] else false

                            DayProgressItem(
                                dayIndex = dayIndex,
                                isCompleted = isCompleted,
                                onToggle = { onToggleDay(habit.id, dayIndex) },
                                isToday = dayIndex == getTodayDayIndex()
                            )

                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Прогресс-бар недели
            val completedDays = habit.dailyStatus.take(7).count { it }
            val progressValue = completedDays.toFloat() / 7f

            LinearProgressIndicator(
                progress = { progressValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(MaterialTheme.shapes.small),
                color = if (progressValue == 1f) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Text(
                text = "$completedDays/7 дней",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}