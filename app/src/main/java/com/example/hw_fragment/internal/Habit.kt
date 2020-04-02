package com.example.hw_fragment.internal

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
    var date: Date) : Serializable
