package com.example.hw_fragment.ui.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.hw_fragment.internal.db.HabitEntity
import com.example.hw_fragment.internal.HabitApplication


class AddNewItemViewModel(private val habitIndex: Int?) : ViewModel() {
    val habit: LiveData<HabitEntity?> = HabitApplication.database.habitDao().getById(habitIndex)

    fun save(habit: HabitEntity) {
        val habitDao = HabitApplication.database.habitDao()
        Log.d("save habit", habitIndex.toString())
        if (habitIndex == null) {
            habitDao.insert(habit) }
        else {
            habit.id = habitIndex
            habitDao.update(habit)
        }
    }
}