package com.readytoride.network.UserApi

data class PostTrainerEntity(
    val email: String,
    val password: String,
    val name: Name,
    val age: Int,
    val role: String,
    val focus: String = "",
    val profilePicture: String = "",
    val description: String = "",
    val achievements: List<Accomplishment> = listOf(),
    val certificates: List<Accomplishment> = listOf(),
    val pictures: List<String> = listOf(),
    val rolePasscode: String
)
