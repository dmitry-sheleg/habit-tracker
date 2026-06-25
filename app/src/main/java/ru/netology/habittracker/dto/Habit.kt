package ru.netology.habittracker.dto

data class Habit(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    // прогресс по дням недели: индекс 0 = понедельник, 6 = воскресенье
    val dailyStatus: MutableList<Boolean> = MutableList(7) { false }
)