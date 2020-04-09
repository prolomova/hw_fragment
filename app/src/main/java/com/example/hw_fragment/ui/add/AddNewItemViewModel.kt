package com.example.hw_fragment.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.Storage


class AddNewItemViewModel(private val habitIndex: Int?) : ViewModel() {
    fun save(habit: Habit) {
        if (habit.id != null)
            Storage.modify(habit)
        else
            Storage.add(habit)
    }

    private val mutableHabit: MutableLiveData<Habit?> = MutableLiveData()
    val habit: LiveData<Habit?> = mutableHabit

    init {
        if (habitIndex != null) {
            Storage.get(habitIndex).let { habit: Habit? -> mutableHabit.postValue(habit)
            }
        }
    }
}