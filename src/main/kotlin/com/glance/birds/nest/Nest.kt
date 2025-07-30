package com.glance.birds.nest

import com.glance.birds.nest.behavior.NestTickHandler
import com.glance.birds.nest.behavior.visual.NestVisualManager
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.data.NestState
import com.glance.birds.nest.occupancy.NestOccupancyController
import com.glance.birds.nest.variant.NestVariant
import com.glance.birds.nest.variant.NestVariantRegistry
import org.bukkit.Location
import java.util.UUID

open class Nest(
    val data: NestData,
    val state: NestState = NestState()
) {

    val uniqueId: UUID get() = data.id

    val location: Location?
        get() = data.pos.toLocation()

    var occupancyController: NestOccupancyController? = null
        set(value) {
            field?.let { tickHandlers.remove(it) } // remove old one if exists
            field = value
            value?.let { registerTickHandler(it) } // register new one if not null
        }

    var tickHandlers: MutableSet<NestTickHandler> = mutableSetOf()

    fun registerTickHandler(ticker: NestTickHandler) {
        this.tickHandlers.add(ticker)
    }

    fun tick() {
        tickHandlers.forEach { it.tick(nest = this) }
    }

    fun tickAsync() {
        tickHandlers.forEach { it.tickAsync(nest = this) }
    }

    fun getVariant(): NestVariant? {
        return NestVariantRegistry.getById(data.variantId)
    }

    fun update() {
        NestVisualManager.updateVisuals(this)
    }

    fun getTrinketKey(): String = "trinkets"

    @Suppress("unchecked_cast")
    fun getTrinkets(): List<String> {
        return data.metadata[getTrinketKey()] as? List<String> ?: emptyList()
    }

    fun setTrinkets(trinkets: List<String>) {
        data.metadata = data.metadata.toMutableMap().apply {
            this[getTrinketKey()] = trinkets
        }
    }

    fun addTrinket(id: String) {
        val updated = getTrinkets().toMutableList().apply { add(id) }
        setTrinkets(updated)
    }

    fun clearTrinkets() {
        data.metadata = data.metadata.toMutableMap().apply {
            remove(getTrinketKey())
        }
    }

}