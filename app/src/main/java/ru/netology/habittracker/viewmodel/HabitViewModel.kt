package ru.netology.habittracker.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.netology.habittracker.dto.Habit
import ru.netology.habittracker.repository.HabitRepository
import ru.netology.habittracker.utils.getTodayDayIndex

class HabitViewModel : ViewModel() {
    private val repo = HabitRepository()

    private val _habits = MutableStateFlow(repo.getAll())
    val habits: StateFlow<List<Habit>> = _habits

    fun addHabit(name: String) {
        if (name.isBlank()) return
        val habit = Habit(name = name)
        repo.add(habit)
        _habits.value = repo.getAll()
    }

    fun toggleDay(habitId: String, dayIndex: Int) {
        val habit = repo.getAll().find { it.id == habitId } ?: return
        val todayIndex = getTodayDayIndex()

        // Разрешаем переключать только сегодняшний день
        if (dayIndex != todayIndex) return

        val updated = habit.copy(dailyStatus = habit.dailyStatus.toMutableList().also {
            it[dayIndex] = !it[dayIndex]
        })
        repo.update(updated)
        _habits.value = repo.getAll()
    }

    fun deleteHabit(id: String) {
        repo.delete(id)
        _habits.value = repo.getAll()
    }

}
