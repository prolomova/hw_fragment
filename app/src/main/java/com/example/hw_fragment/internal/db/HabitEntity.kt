package com.example.hw_fragment.internal.db

import androidx.room.*
import com.example.hw_fragment.internal.HabitType
import java.util.*


@Entity(tableName = "habit")
@TypeConverters(Converter::class)
data class HabitEntity(
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val priority: Int,
    @ColumnInfo val type: HabitType,
    @ColumnInfo val periodicity: Int,
    @ColumnInfo val quantity: Int,
    @ColumnInfo val color: Int,
    @ColumnInfo val date: Date
) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}