package com.glance.birds.nest.data

import com.glance.birds.nest.behavior.NestTickHandler
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.behavior.visual.NestVisualManager
import com.glance.birds.nest.occupancy.NestOccupancyController
import com.glance.birds.nest.variant.NestVariant
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.util.world.WorldBlockPos
import java.util.UUID

data class NestData(
    val variantId: String,
    val pos: WorldBlockPos,
    val type: NestType,
    var size: Int = 1,
    var dropMode: NestDropMode = NestDropMode.SURVIVAL_ONLY,
    var metadata: Map<String, Any> = mapOf(),
    var lastUpdated: Long = System.currentTimeMillis(),
    val id: UUID = UUID.randomUUID(),
) {

    fun quickInfo(): String {
        return "Nest(Variant: $variantId, Position: ${pos.coords}, Type: $type, Size: $size)"
    }

}