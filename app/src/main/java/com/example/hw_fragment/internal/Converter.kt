package com.example.hw_fragment.internal

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.example.hw_fragment.R
import org.json.JSONObject
import java.util.*
import res.color.*


class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

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
    fun FromColorToInt(color: ColorStateList?): Int? {
        return color?.defaultColor
    }

    @TypeConverter
    fun FromIntToColor(p: Int?): ColorStateList? {
        return ColorStateList.valueOf(p!!)
    }
}