package com.readytoride.network.LessonApi

data class LessonEntity(
    val _id: String,
    val trainer: String,
    val booked: Boolean,
    val arena: String,
    val day: String,
    val startHour: Int,
    val horse: HorseForLesson = HorseForLesson("Horst", "")

)

data class HorseForLesson(val name: String, val id: String)
