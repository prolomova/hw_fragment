package com.example.hw_fragment.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Storage {
    private var id = 0
    private val habits : MutableLiveData<MutableMap<Int, Habit>> = MutableLiveData()

    init {
        habits.value = mutableMapOf()
    }

    val index: Int
        get() = id

    fun add(habit: Habit) {
        habit.id = id
        habits.value?.set(id, habit)
        id++
    }

    fun get(index: Int) : Habit? {
        for (i in habits.value?.keys?.indices!!) {
            if (habits.value?.get(i)?.id == index) {
                return habits.value!![i]
            }
        }
        return null
    }

    fun edit(habit: Habit) {
        for (i in habits.value?.keys?.indices!!) {
            if (habits.value?.get(i)?.id == habit.id) {
                habits.value?.set(i, habit)
                return
            }
        }
    }

    fun remove(habit: Habit) {
        if (habit.id != null)
            habits.value?.remove(habit.id!!)
    }

    fun getAllHabitsWithType(type: HabitType): List<Habit> {
        return habits.value?.values?.filter { habit -> habit.type == type }!!
    }

    fun getAllHabits() : LiveData<List<Habit>> {
        val ld = MutableLiveData<List<Habit>>()
        ld.value = habits.value?.values?.toList()
        return ld
    }
}