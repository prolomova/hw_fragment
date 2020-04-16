package com.example.hw_fragment.internal

import android.app.Application
import androidx.room.Room


class HabitApplication : Application() {
    companion object {
        lateinit var database: Database
    }

    override fun onCreate() {
        super.onCreate()

        database = Room
            .databaseBuilder(
                applicationContext,
                Database::class.java, "HabitDataBase"
            )
            .allowMainThreadQueries()
            .build()
    }
}