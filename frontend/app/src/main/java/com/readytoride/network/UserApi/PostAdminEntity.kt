package com.readytoride.network.UserApi

data class PostAdminEntity(
    val email: String,
    val password: String,
    val name: Name,
    val age: Int,
    val role: String,
    val rolePasscode: String
)
