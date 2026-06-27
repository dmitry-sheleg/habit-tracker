package ru.netology.habittracker.repository

import ru.netology.habittracker.dto.Habit

class HabitRepositoryImpl : HabitRepository {

    private val habits = mutableListOf<Habit>()
    private var nextId = 1L

    override fun getAll(): List<Habit> = habits.toList()

    override fun add(habit: Habit): Boolean {
        val habitWithId = habit.copy(id = nextId++)
        return habits.add(habitWithId)
    }

    override fun update(updated: Habit) {
        val index = habits.indexOfFirst { it.id == updated.id }
        if (index >= 0) {
            habits[index] = updated.copy()
        }
    }

    override fun delete(id: Long) {
        habits.removeAll { it.id == id }
    }
}
