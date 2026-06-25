package ru.netology.habittracker.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.netology.habittracker.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HabitListScreen(
    viewModel: HabitViewModel,
    navController: NavController
) {
    val habits = viewModel.habits.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Трекер привычек") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("create") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                items(items = habits, key = { it.id }) { habit ->
                    // Исправление 1: DismissState требует initialValue в этой версии библиотеки
                    val dismissState = remember { DismissState(initialValue = DismissValue.Default) }

                    // Исправление 2: animateFloatAsState возвращает State<Float>, поэтому нельзя использовать "by"
                    val swipeProgress = animateFloatAsState(targetValue = dismissState.progress.fraction)

                    // Проверяем, был ли свайп в любом направлении
                    val isDismissed = dismissState.isDismissed(DismissDirection.StartToEnd) ||
                            dismissState.isDismissed(DismissDirection.EndToStart)

                    LaunchedEffect(isDismissed) {
                        if (isDismissed) {
                            viewModel.deleteHabit(habit.id)
                            // Сбрасываем состояние свайпа после удаления
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
