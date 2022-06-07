package com.readytoride.ui.booking

import com.readytoride.R

class BookingsDatasource {
    fun loadBookings (): List<Bookings> {
        return listOf<Bookings>(
            Bookings(R.string.id1, R.string.id1, R.string.trainername1, R.string.horsename1, R.string.day1, R.string.time1, R.string.location1),
            Bookings(R.string.id2, R.string.id2, R.string.trainername2, R.string.horsename2, R.string.day1, R.string.time2, R.string.location2),
            Bookings(R.string.id3, R.string.id3, R.string.trainername3, R.string.horsename3, R.string.day2, R.string.time3, R.string.location3),
            Bookings(R.string.id4, R.string.id4, R.string.trainername4, R.string.horsename4, R.string.day2, R.string.time4, R.string.location4),
            Bookings(R.string.id5, R.string.id5, R.string.trainername5, R.string.horsename5, R.string.day3, R.string.time5, R.string.location1),
            Bookings(R.string.id1, R.string.id6, R.string.trainername1, R.string.horsename6, R.string.day4, R.string.time6, R.string.location2),
            Bookings(R.string.id2, R.string.id7, R.string.trainername2, R.string.horsename7, R.string.day5, R.string.time7, R.string.location3),
            Bookings(R.string.id3, R.string.id8, R.string.trainername3, R.string.horsename8, R.string.day5, R.string.time1, R.string.location4),
            Bookings(R.string.id4, R.string.id9, R.string.trainername4, R.string.horsename9, R.string.day5, R.string.time2, R.string.location1),
            Bookings(R.string.id5, R.string.id10, R.string.trainername5, R.string.horsename10, R.string.day7, R.string.time3, R.string.location2)
        )

    }
}