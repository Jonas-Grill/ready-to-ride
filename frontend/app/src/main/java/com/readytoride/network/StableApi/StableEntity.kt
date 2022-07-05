package com.readytoride.network.StableApi

data class StableEntity(
    val _id: String = "",
    val name: String,
    val description: String,
    val arenas: List<Arena>,
    val boxes: List<Box>
)

data class Arena(val name: String, val size: Int)

data class Box(val name: String, val price: Int, val size: Double, val count: Int)
