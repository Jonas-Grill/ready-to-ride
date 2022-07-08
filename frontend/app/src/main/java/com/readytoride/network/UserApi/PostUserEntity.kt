package com.readytoride.network.UserApi

data class PostUserEntity(
    val email: String,
    val password: String,
    val name: Name,
    val age: Int,
    val role: String,
    val weight: Int,
    val height: Int,
    val proficiency: String
)
