package com.example.hw_fragment.ui.home

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.HabitType
import com.example.hw_fragment.internal.Storage
import java.util.*


class HabitListViewModel(private val filter: (Habit) -> Boolean) : ViewModel() {
    val nameSubstring: MutableLiveData<String> = MutableLiveData()
    val habits: MediatorLiveData<List<Habit>> = MediatorLiveData()

    private fun getHabits() : List<Habit>? {
        return Storage.getAllHabits().value?.filter(filter)
    }

    init {
        habits.value = getHabits()
        habits.addSource(Storage.getAllHabits(), Observer { newHabits ->
            habits.value = newHabits.filter { nameSubstring.value.isNullOrBlank() ||
                    nameSubstring.value!!.toLowerCase(Locale.getDefault()) in
                    it.name.toLowerCase(Locale.getDefault()) }
        })
        habits.addSource(nameSubstring, Observer { updatedSubstring ->
            habits.value = Storage.getAllHabits().value?.filter {
                updatedSubstring.isNullOrEmpty() ||
                        updatedSubstring.toLowerCase(Locale.getDefault()) in
                        it.name.toLowerCase(Locale.getDefault())
            }
        })
    }

    fun sortDate() {
        habits.value = habits.value?.sortedBy { it.date }
    }

    fun sortDateDescending() {
        habits.value = habits.value?.sortedByDescending { it.date }
    }
}