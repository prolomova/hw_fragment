package com.example.hw_fragment.internal.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [HabitEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}