package ru.netology.habittracker.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import ru.netology.habittracker.dto.Habit
import java.io.File
import java.io.IOException

class HabitRepositoryFileImpl(context: Context) : HabitRepository {

    companion object {
        private const val FILE_NAME = "habits.json"
    }

    private val habitsFile: File = File(context.filesDir, FILE_NAME)
    private val gson = Gson()

    // Тип для десериализации List<Habit>
    private val habitListType = object : TypeToken<List<Habit>>() {}.type

    private var habits = mutableListOf<Habit>()
    private var nextId = 1L

    init {
        loadFromJson()
    }

    override fun getAll(): List<Habit> = habits.toList()

    override fun add(habit: Habit): Boolean {
        val habitWithId = habit.copy(id = nextId++)
        habits.add(habitWithId)
        saveToJson()
        return true
    }

    override fun update(updated: Habit) {
        val index = habits.indexOfFirst { it.id == updated.id }
        if (index >= 0) {
            habits[index] = updated.copy()
            saveToJson()
        }
    }

    override fun delete(id: Long) {
        val removed = habits.removeAll { it.id == id }
        if (removed) {
            saveToJson()
        }
    }

    private fun loadFromJson() {
        if (!habitsFile.exists()) return

        try {
            val json = habitsFile.readText()
            if (json.isBlank()) return

            val loaded: List<Habit>? = gson.fromJson(json, habitListType)

            if (loaded == null) {
                habits = mutableListOf()
                nextId = 1L
                return
            }

            habits = loaded.toMutableList()

            nextId = if (habits.isEmpty()) {
                1L
            } else {
                (habits.maxOfOrNull { it.id } ?: 0L) + 1L
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            habits = mutableListOf()
            nextId = 1L
        } catch (e: IOException) {
            e.printStackTrace()
            habits = mutableListOf()
            nextId = 1L
        }
    }



    private fun saveToJson() {
        try {
            val json = gson.toJson(habits)
            habitsFile.writeText(json)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

