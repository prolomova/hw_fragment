package com.example.hw_fragment.internal

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import java.io.Serializable
import java.util.*


data class Habit(
    var id: Int?,
    val name: String,
    val description: String,
    val priority: Int,
    val type: HabitType,
    val quantity: Int,
    val periodicity: Int,
    val color: ColorStateList?,
    var date: Date) : Serializable
