package com.glance.birds.nest.data

import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.visual.NestVisualState
import com.glance.birds.util.world.WorldBlockPos
import java.util.UUID

data class NestData(
    val pos: WorldBlockPos,
    val variantId: String,
    val type: NestType,
    val size: Int = 1,
    val state: NestVisualState = NestVisualState(),
    var metadata: Map<String, Any> = mapOf(),
    var lastUpdated: Long = System.currentTimeMillis(),
    val id: UUID = UUID.randomUUID(),
)