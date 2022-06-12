package com.readytoride.network.UserApi

import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class UserEntity(
    val _id: String,
    val email: String,
    val name: Name,
    val age: Int,
    val focus: String,
    val profilePicture: String
)

data class Name(val firstName: String, val lastName: String)
