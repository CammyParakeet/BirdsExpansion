package com.glance.birds.nest.data

import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.visual.NestVisualState
import com.glance.birds.util.world.WorldBlockPos
import java.util.UUID

data class NestData(
    val variantId: String,
    val pos: WorldBlockPos,
    val type: NestType,
    var size: Int = 1,
    var visualState: NestVisualState = NestVisualState(),
    var metadata: Map<String, Any> = mapOf(),
    var lastUpdated: Long = System.currentTimeMillis(),
    val id: UUID = UUID.randomUUID(),
) {

    fun quickInfo(): String {
        return "Nest(Variant: $variantId, Position: ${pos.coords}, Type: $type, Size: $size)"
    }

    fun getTrinketKey(): String = "trinkets"

    @Suppress("unchecked_cast")
    fun getTrinkets(): List<String> {
        return metadata[getTrinketKey()] as? List<String> ?: emptyList()
    }

    fun setTrinkets(trinkets: List<String>) {
        metadata = metadata.toMutableMap().apply {
            this[getTrinketKey()] = trinkets
        }
    }

    fun addTrinket(id: String) {
        val updated = getTrinkets().toMutableList().apply { add(id) }
        setTrinkets(updated)
    }

    fun clearTrinkets() {
        metadata = metadata.toMutableMap().apply {
            remove(getTrinketKey())
        }
    }

}

fun NestData.ensureVisualState(): NestData {
    return if (this.visualState == null) {
        this.copy(visualState = NestVisualState())
    } else {
        this
    }
}