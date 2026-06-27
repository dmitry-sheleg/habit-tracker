package ru.netology.habittracker.utils

import java.time.LocalDate

fun getTodayDayIndex(): Int = LocalDate.now().dayOfWeek.run {
    // DayOfWeek: MONDAY=1 ... SUNDAY=7
    value - 1 // MONDAY→0 ... SUNDAY→6
}
