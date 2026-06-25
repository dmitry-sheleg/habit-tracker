package ru.netology.habittracker.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.netology.habittracker.dto.Habit
import ru.netology.habittracker.utils.getTodayDayIndex

@Composable
fun HabitCard(
    habit: Habit,
    onToggleDay: (String, Int) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            Column {
                Text(
                    text = habit.name,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    items(7) { dayIndex ->
                        DayProgressItem(
                            dayIndex = dayIndex,
                            isCompleted = habit.dailyStatus[dayIndex],
                            onToggle = { onToggleDay(habit.id, dayIndex) },
                            isToday = dayIndex == getTodayDayIndex() // вынести в ViewModel или общий util
                        )
                    }
                }
            }
            // кнопка удаления будет реализована через SwipeToDismiss на уровне списка
        }
    }
}
