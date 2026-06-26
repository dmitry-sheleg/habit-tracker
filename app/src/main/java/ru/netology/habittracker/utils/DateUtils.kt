package ru.netology.habittracker.utils

import java.time.LocalDate
import java.util.Calendar

fun getTodayDayIndex1(): Int {
    // Calendar.DAY_OF_WEEK: 2=MON, 3=TUE, ..., 1=SUN
    return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2 // Пн=0, Вс=6
}

fun getTodayDayIndex(): Int = LocalDate.now().dayOfWeek.run {
    // DayOfWeek: MONDAY=1 ... SUNDAY=7
    value - 1 // MONDAY→0 ... SUNDAY→6
}