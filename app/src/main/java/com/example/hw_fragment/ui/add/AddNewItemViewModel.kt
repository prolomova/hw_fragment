package com.example.hw_fragment.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.Storage


class AddNewItemViewModel(private val editedHabit: Habit?) : ViewModel() {
    fun save(habit: Habit) {
        if (habit.id != null)
            Storage.edit(habit)
        else
            Storage.add(habit)
    }

    private val mutableLD: MutableLiveData<Habit?> = MutableLiveData()
    val habit: LiveData<Habit?> = mutableLD

    init {
        mutableLD.postValue(editedHabit)
    }
}