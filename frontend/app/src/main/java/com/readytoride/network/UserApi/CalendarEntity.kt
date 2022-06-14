package com.readytoride.network.UserApi

data class CalendarEntity(
    val _id: String,
    val traienr: String,
    val booked: Boolean,
    val arena: String,
    val day: String,
    val startHour: Int,
    val horse: HorseInCalendar
)

data class HorseInCalendar(
    val name: String,
    val id: String
)
