package com.readytoride.ui.booking

import androidx.lifecycle.ViewModel

class BookingViewModel : ViewModel() {
    private val trainers: MutableList<String> = mutableListOf()
    private val horses: MutableList<String> = mutableListOf()

    fun getTrainers(): MutableList<String> {
        return trainers
    }

    fun addTrainer(trainer: String) {
        trainers.add(trainer)
    }

    fun removeTrainer(trainer: String) {
        trainers.remove(trainer)
    }

    fun isInTrainers(trainer: String): Boolean {
        return trainers.contains(trainer)
    }

    fun getHorses(): MutableList<String> {
        return horses
    }

    fun addHorse(horse: String) {
        horses.add(horse)
    }

    fun removeHorse(horse: String) {
        horses.remove(horse)
    }

    fun isInHorses(horse: String): Boolean {
        return horses.contains(horse)
    }
}