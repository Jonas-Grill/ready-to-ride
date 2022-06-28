package com.readytoride.network.NewsApi

import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class NewsEntity(
    val _id: String,
    val caption: String,
    val text: String,
    val adressess: List<String>
)
