package com.glance.birds.nest.data

import java.util.*

data class NestState(
    var eggCount: Int = 0,
    var featherCount: Int = 0,
    var isDamaged: Boolean = false,

    // Birds assigned to this nest (home)
    var assignedBirds: MutableSet<UUID> = mutableSetOf(),

    // Birds currently inside the nest or sitting visibly
    var residingBirds: MutableSet<UUID> = mutableSetOf()
) {
    val hasEggs: Boolean get() = eggCount > 0
    val hasFeathers: Boolean get() = featherCount > 0
    val isOccupied: Boolean get() = residingBirds.isNotEmpty()

    fun emptyContents() {
        eggCount = 0
        featherCount = 0
    }

    fun withEggs(eggs: Int) {
        eggCount = eggs
    }

    fun withFeathers(feathers: Int) {
        featherCount = feathers
    }

}
