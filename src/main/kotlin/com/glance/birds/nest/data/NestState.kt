package com.glance.birds.nest.data

data class NestState(
    var eggCount: Int = 0,
    var featherCount: Int = 0,
    var isDamaged: Boolean = false,
    var isOccupied: Boolean = false
) {
    val hasEggs: Boolean
        get() = eggCount > 0

    val hasFeathers: Boolean
        get() = featherCount > 0

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
