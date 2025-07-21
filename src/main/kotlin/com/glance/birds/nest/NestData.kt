package com.glance.birds.nest

import com.glance.birds.util.world.WorldBlockPos

data class NestData(
    val pos: WorldBlockPos,
    val type: NestType,
    val size: Int = 1,
    var metadata: Map<String, Any> = mapOf(),
    var lastUpdated: Long = System.currentTimeMillis()
)

// todo change this to something more like state

enum class NestType {
    GROUND,
    TREE,
    CLIFF,
    WATER
}