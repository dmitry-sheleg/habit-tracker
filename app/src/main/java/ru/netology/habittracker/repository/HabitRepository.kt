package ru.netology.habittracker.repository

import ru.netology.habittracker.dto.Habit

interface HabitRepository {
    fun getAll(): List<Habit>
    fun add(habit: Habit): Boolean
    fun update(updated: Habit)
    fun delete(id: Long)
}