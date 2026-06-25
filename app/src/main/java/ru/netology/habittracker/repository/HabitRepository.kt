package ru.netology.habittracker.repository

import ru.netology.habittracker.dto.Habit

class HabitRepository {
    private val _habits = mutableListOf<Habit>()
    val habits: List<Habit> get() = _habits.toList()

    fun getAll(): List<Habit> = _habits.toList()
    fun add(habit: Habit) = _habits.add(habit)
    fun update(updated: Habit) {
        val index = _habits.indexOfFirst { it.id == updated.id }
        if (index >= 0) _habits[index] = updated.copy()
    }
    fun delete(id: String) {
        _habits.removeAll { it.id == id }
    }
}