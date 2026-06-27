package ru.netology.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.netology.habittracker.composables.CreateHabitScreen
import ru.netology.habittracker.composables.HabitListScreen
import ru.netology.habittracker.repository.HabitRepositoryFileImpl
import ru.netology.habittracker.ui.theme.HabitTrackerTheme
import ru.netology.habittracker.viewmodel.HabitViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Создаём репозиторий, передавая контекст Activity
        val repo = HabitRepositoryFileImpl(this)

        // 2. Создаём ViewModel, передавая репозиторий в конструктор
        val viewModel = HabitViewModel(repo)

        setContent {
            HabitTrackerTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "list"
                ) {
                    composable("list") {
                        HabitListScreen(viewModel = viewModel, navController = navController)
                    }
                    composable("create") {
                        CreateHabitScreen(viewModel = viewModel, navController = navController)
                    }
                }
            }
        }
    }
}
