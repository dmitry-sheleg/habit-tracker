package ru.netology.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.netology.habittracker.composables.CreateHabitScreen
import ru.netology.habittracker.composables.HabitListScreen
import ru.netology.habittracker.ui.theme.HabitTrackerTheme
import ru.netology.habittracker.viewmodel.HabitViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabitTrackerTheme {
                val navController = rememberNavController()
                val viewModel: HabitViewModel = viewModel()

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