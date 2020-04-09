package com.example.hw_fragment.internal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Storage {
    companion object {
        private var id = 0

        private val mutableHabits: MutableLiveData<MutableMap<Int, Habit>> =
            MutableLiveData(mutableMapOf())

        val habits: LiveData<MutableMap<Int, Habit>> = mutableHabits

        val index: Int
            get() = id

        fun add(habit: Habit) {
            habit.id = id
            habits.value?.set(id, habit)
            id++
        }

        fun get(index: Int): Habit? {
            return mutableHabits.value?.get(index)
        }

        fun set(index: Int, habit: Habit) {
            mutableHabits.value?.set(index, habit)
        }

        fun modify(habit: Habit) {
            mutableHabits.value?.set(habit.id!!, habit)
        }

        fun remove(habit: Habit) {
            if (habit.id != null)
                habits.value?.remove(habit.id!!)
        }

        fun size(): Int {
            return habits.value?.size!!
        }

        fun getAllHabitsWithType(type: HabitType): List<Habit> {
            return habits.value?.values?.filter { habit -> habit.type == type }!!
        }
    }
}