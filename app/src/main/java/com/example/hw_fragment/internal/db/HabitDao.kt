package com.example.hw_fragment.internal.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAll(): LiveData<List<HabitEntity>>

    @Query("SELECT * FROM habit WHERE id=:id")
    fun getById(id: Int?): LiveData<HabitEntity?>

    @Insert
    fun insert(habit: HabitEntity)

    @Update
    fun update(habit: HabitEntity)

    @Delete
    fun delete(habit: HabitEntity)
}