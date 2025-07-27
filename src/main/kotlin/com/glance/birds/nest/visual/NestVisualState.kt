package com.glance.birds.nest.visual

data class NestVisualState(
    var hasEggs: Boolean = false,
    var eggCount: Int = 0,

    var hasFeathers: Boolean = false,
    var featherCount: Int = 0,

    var isDamaged: Boolean = false,
    var isOccupied: Boolean = false
)
