package com.readytoride.network.HorseApi

data class HorseEntity(
    val _id: String,
    val name: String,
    val height: Int,
    val race: String,
    val age: Int,
    val colour: String,
    val difficultyLevel: String,
    val profilePicture: String,
    val description: String = "Test",
    val pictures: List<String> = listOf()
)
