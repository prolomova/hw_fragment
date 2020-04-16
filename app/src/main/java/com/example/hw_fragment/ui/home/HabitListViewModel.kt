package com.example.hw_fragment.ui.home

import androidx.lifecycle.*
import com.example.hw_fragment.internal.HabitEntity
import com.example.hw_fragment.internal.HabitApplication
import java.util.*


class HabitListViewModel : ViewModel() {
    private val allHabits: LiveData<List<HabitEntity>> = HabitApplication.database.habitDao().getAll()
    val nameSubstring: MutableLiveData<String> = MutableLiveData()


    val habits: MediatorLiveData<List<HabitEntity>> = MediatorLiveData()

    init {
        habits.addSource(allHabits) { newHabits ->

            habits.value = newHabits.toList()
        }
        habits.addSource(nameSubstring) { updatedSubstring ->
            habits.value = allHabits.value?.filter { updatedSubstring.isNullOrBlank() ||
                        updatedSubstring in it.name.toLowerCase(Locale.getDefault())
            } ?: listOf()
        }
    }

    fun sortDate() {
        habits.value = habits.value?.sortedBy { it.date }
    }

    fun sortDateDescending() {
        habits.value = habits.value?.sortedByDescending { it.date }
    }

    fun updateNameFilter(newString: String) {
        nameSubstring.value = newString.toLowerCase(Locale.getDefault())
    }
}