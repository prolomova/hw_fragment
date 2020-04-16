package com.example.hw_fragment.internal.db

import android.content.res.ColorStateList
import androidx.room.TypeConverter
import com.example.hw_fragment.internal.HabitType
import java.util.*


class Converter {
    @TypeConverter
    fun typeToString(type: HabitType?): String? {
        return type?.toString()
    }

    @TypeConverter
    fun FromStringToType(s: String?): HabitType? {
        return when (s) {
            "GOOD" -> HabitType.GOOD
            else -> HabitType.BAD
        }
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun FromColorToInt(color: ColorStateList?): Int? {
        return color?.defaultColor
    }

    @TypeConverter
    fun FromIntToColor(p: Int?): ColorStateList? {
        return ColorStateList.valueOf(p!!)
    }
}