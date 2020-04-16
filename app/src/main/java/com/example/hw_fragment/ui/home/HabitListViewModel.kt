package com.example.hw_fragment.ui.home

import androidx.lifecycle.*
import com.example.hw_fragment.internal.db.HabitEntity
import com.example.hw_fragment.internal.HabitApplication
import java.util.*
import com.example.hw_fragment.internal.SortOrder


class HabitListViewModel : ViewModel() {
    private val allHabits: LiveData<List<HabitEntity>> = HabitApplication.database.habitDao().getAll()
    val nameSubstring: MutableLiveData<String> = MutableLiveData()
    private val sortOrder = MutableLiveData<SortOrder>()

    val habits: MediatorLiveData<List<HabitEntity>> = MediatorLiveData()

    init {
        habits.addSource(allHabits) { newHabits ->
            when(sortOrder.value) {
                SortOrder.ASCENDING -> habits.value = newHabits.toList()
                else -> habits.value = newHabits.toList().sortedByDescending { it.date }
            }
        }
        habits.addSource(nameSubstring) { updatedSubstring ->
            habits.value = when(sortOrder.value) {
                SortOrder.ASCENDING -> allHabits.value?.filter { updatedSubstring.isNullOrBlank() ||
                        updatedSubstring in it.name.toLowerCase(Locale.getDefault())
                } ?: listOf()
                else -> allHabits.value?.filter { updatedSubstring.isNullOrBlank() ||
                        updatedSubstring in it.name.toLowerCase(Locale.getDefault())
                }?.sortedByDescending { it.date }
            }

        }
    }

    fun sortDate() {
        sortOrder.value = SortOrder.ASCENDING
        habits.value = habits.value?.sortedBy { it.date }
    }

    fun sortDateDescending() {
        sortOrder.value = SortOrder.DESCENDING
        habits.value = habits.value?.sortedByDescending { it.date }
    }

    fun updateNameFilter(newString: String) {
        nameSubstring.value = newString.toLowerCase(Locale.getDefault())
    }
}