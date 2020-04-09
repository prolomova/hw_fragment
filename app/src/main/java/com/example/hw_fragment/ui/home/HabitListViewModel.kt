package com.example.hw_fragment.ui.home

import androidx.lifecycle.*
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.Storage
import java.util.*


class HabitListViewModel : ViewModel() {
    val nameSubstring: MutableLiveData<String> = MutableLiveData()

    val habits: MediatorLiveData<List<Habit>> = MediatorLiveData()

    init {
        habits.addSource(Storage.habits) { newHabits ->
            habits.value = newHabits.values.toList()
        }
        habits.addSource(nameSubstring) { updatedSubstring ->
            habits.value = Storage.habits.value?.values?.filter { updatedSubstring.isNullOrBlank() ||
                        updatedSubstring in it.name.toLowerCase(Locale.getDefault())
            }
        }
    }

    fun sortDate() {
        habits.value = habits.value?.sortedBy { it.date }
    }

    fun sortDateDescending() {
        habits.value = habits.value?.sortedByDescending { it.date }
    }
}