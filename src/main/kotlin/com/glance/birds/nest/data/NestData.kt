package com.glance.birds.nest.data

import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.behavior.visual.NestVisualManager
import com.glance.birds.nest.variant.NestVariant
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.species.BirdSpeciesRegistry
import com.glance.birds.util.world.WorldBlockPos
import org.bukkit.entity.Mob
import java.util.UUID

data class NestData(
    val variantId: String,
    val pos: WorldBlockPos,
    val type: NestType,
    var size: Int = 1,
    var state: NestState = NestState(),
    var dropMode: NestDropMode = NestDropMode.SURVIVAL_ONLY,
    var metadata: Map<String, Any> = mapOf(),
    var lastUpdated: Long = System.currentTimeMillis(),
    val id: UUID = UUID.randomUUID(),
) {

    fun getVariant(): NestVariant? {
        return NestVariantRegistry.getById(variantId)
    }

    fun canFitMob(mob: Mob): Boolean {
        val species = BirdSpeciesRegistry.get(mob) ?: return false
        val maxSpace = getVariant()?.maxOccupantSpace ?: 2
        val current = state.getCurrentOccupantSpace()
        val incoming = species.getNestSizeCost(mob)
        return current + incoming <= maxSpace
    }

    fun update() {
        NestVisualManager.updateVisuals(this)
    }

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