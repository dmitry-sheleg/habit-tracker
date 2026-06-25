package ru.netology.habittracker.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.netology.habittracker.dto.Habit
import ru.netology.habittracker.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HabitListScreen(
    viewModel: HabitViewModel,
    navController: NavController
) {
    val habits = viewModel.habits.collectAsState().value

    // Пересчитываем прогресс каждый раз, когда меняется список habits
    val daysProgress = remember(habits) {
        calculateWeeklyProgress(habits)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Трекер привычек") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("create") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Панель недельного прогресса
            WeeklyProgressPanel(progressValues = daysProgress)

            Spacer(modifier = Modifier.height(16.dp))

            // Список привычек
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(items = habits, key = { it.id }) { habit ->
                    val dismissState = remember { DismissState (DismissValue.Default) }

                    val isDismissed = dismissState.isDismissed(DismissDirection.StartToEnd) ||
                            dismissState.isDismissed(DismissDirection.EndToStart)

                    LaunchedEffect(isDismissed) {
                        if (isDismissed) {
                            viewModel.deleteHabit(habit.id)
                            dismissState.reset()
                        }
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier.fillMaxWidth(),
                        background = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Удалить",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            HabitCard(
                                habit = habit,
                                onToggleDay = { habitId, dayIndex ->
                                    viewModel.toggleDay(habitId, dayIndex)
                                },
                                onDelete = {}
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun WeeklyProgressPanel(progressValues: List<Int>) {
    val daysOfWeek = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysOfWeek.forEachIndexed { index, dayName ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.width(IntrinsicSize.Min)
            ) {
                Text(
                    text = dayName,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                ProgressRing(
                    progress = progressValues[index],
                    size = 32.dp,
                    strokeWidth = 3.dp,
                    color = when {
                        progressValues[index] == 100 -> Color(0xFF4CAF50)
                        progressValues[index] > 0 -> Color.Green
                        else -> Color.LightGray
                    },
                    backgroundColor = Color.LightGray
                )
            }
        }
    }
}

/**
 * Считает суммарный процент выполнения по каждому дню недели.
 * Использует поле dailyStatus из модели Habit.
 */
private fun calculateWeeklyProgress(habits: List<Habit>): List<Int> {
    if (habits.isEmpty()) {
        return List(7) { 0 }
    }

    val totalHabits = habits.size.toFloat()
    val completedPerDay = IntArray(7) // теперь без лямбды — все элементы автоматически 0

    for (habit in habits) {
        habit.dailyStatus.forEachIndexed { dayIndex, isCompleted ->
            if (dayIndex in 0..6 && isCompleted) {
                completedPerDay[dayIndex]++
            }
        }
    }

    return completedPerDay.map { completed ->
        ((completed / totalHabits) * 100).toInt()
    }
}




//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.DismissDirection
//import androidx.compose.material.DismissState
//import androidx.compose.material.DismissValue
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.SwipeToDismiss
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import ru.netology.habittracker.viewmodel.HabitViewModel
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
//@Composable
//fun HabitListScreen(
//    viewModel: HabitViewModel,
//    navController: NavController
//) {
//    val habits = viewModel.habits.collectAsState().value
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text("Трекер привычек") }
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = { navController.navigate("create") }) {
//                Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить")
//            }
//        }
//    ) { paddingValues ->
//        Box(modifier = Modifier.padding(paddingValues)) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 8.dp)
//            ) {
//                items(items = habits, key = { it.id }) { habit ->
//                    // Исправление 1: DismissState требует initialValue в этой версии библиотеки
//                    val dismissState = remember { DismissState(initialValue = DismissValue.Default) }
//
//                    // Исправление 2: animateFloatAsState возвращает State<Float>, поэтому нельзя использовать "by"
//                    val swipeProgress = animateFloatAsState(targetValue = dismissState.progress.fraction)
//
//                    // Проверяем, был ли свайп в любом направлении
//                    val isDismissed = dismissState.isDismissed(DismissDirection.StartToEnd) ||
//                            dismissState.isDismissed(DismissDirection.EndToStart)
//
//                    LaunchedEffect(isDismissed) {
//                        if (isDismissed) {
//                            viewModel.deleteHabit(habit.id)
//                            // Сбрасываем состояние свайпа после удаления
//                            dismissState.reset()
//                        }
//                    }
//
//                    SwipeToDismiss(
//                        state = dismissState,
//                        modifier = Modifier.fillMaxWidth(),
//                        background = {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(end = 16.dp)
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Delete,
//                                    contentDescription = "Удалить",
//                                    tint = MaterialTheme.colorScheme.error,
//                                    modifier = Modifier.align(Alignment.CenterEnd)
//                                )
//                            }
//                        },
//                        dismissContent = {
//                            HabitCard(
//                                habit = habit,
//                                onToggleDay = { habitId, dayIndex ->
//                                    viewModel.toggleDay(habitId, dayIndex)
//                                },
//                                onDelete = {}
//                            )
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
