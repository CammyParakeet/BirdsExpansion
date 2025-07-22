package com.glance.birds.nest.visual

data class NestVisualState(
    val hasEggs: Boolean = false,
    val eggCount: Int = 0,
    val hasFeathers: Boolean = false,
    val isDamaged: Boolean = false,
    val isOccupied: Boolean = false
)
