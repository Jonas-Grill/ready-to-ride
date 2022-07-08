package com.readytoride.network.NewsApi

data class PostNewsEntity(
    val caption: String,
    val text: String,
    val addressees: String = ""
)
