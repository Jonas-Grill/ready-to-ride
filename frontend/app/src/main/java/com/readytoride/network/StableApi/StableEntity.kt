package com.readytoride.network.StableApi

data class StableEntity(
    val _id: String = " ",
    val name: String,
    val description: String,
    var arenas: List<Arena>,
    var boxes: List<Box>
)

data class Arena(var name: String, var size: Int)

data class Box(var name: String, var price: Int, var size: Double, var count: Int)
