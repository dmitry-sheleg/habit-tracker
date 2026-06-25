package ru.netology.habittracker.utils

import java.util.Calendar

fun getTodayDayIndex(): Int {
    // Calendar.DAY_OF_WEEK: 2=MON, 3=TUE, ..., 1=SUN
    return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2 // Пн=0, Вс=6
}
