package com.readytoride.network.UserApi

import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class UserEntity(
    val _id: String = "",
    val email: String,
    val password: String = "",
    val name: Name,
    val age: Int,
    val role: String = "",

//for BaseUser only
    val weight: Int = 0,
    val height: Int = 0,
    val proficiency: String = "",

//for Admin only
    val adminPasscode: String = "",
    val trainerPasscode: String = "",

//for Trainer only
    val focus: String = "",
    val profilePicture: String = "",
    val description: String = "",
    val achievements: List<Accomplishment> = listOf(),
    val certificates: List<Accomplishment> = listOf(),
    val pictures: List<String> = listOf()
)

data class Name(val firstName: String, val lastName: String)

data class Accomplishment(val year: Int, val name: String)
