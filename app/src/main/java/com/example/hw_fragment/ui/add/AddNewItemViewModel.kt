package com.example.hw_fragment.ui.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_fragment.internal.db.HabitEntity
import com.example.hw_fragment.internal.HabitApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddNewItemViewModel(private val habitIndex: Int?) : ViewModel() {
    val habit: LiveData<HabitEntity?> = HabitApplication.database.habitDao().getById(habitIndex)

    fun save(habit: HabitEntity) {
        viewModelScope.launch(Dispatchers.Default) {
            val habitDao = HabitApplication.database.habitDao()
            Log.d("save habit", habitIndex.toString())
            if (habitIndex == null) {
                withContext(Dispatchers.IO) { habitDao.insert(habit) }
            }
            else {
                habit.id = habitIndex
                withContext(Dispatchers.IO) { habitDao.update(habit) }
            }
        }

    }
}