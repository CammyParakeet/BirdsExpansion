package com.glance.birds.nest.behavior.visual

import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.data.NestState
import com.glance.birds.nest.variant.NestVariantRegistry

object NestVisualManager {

    fun getHandler(nest: NestData): NestVisualHandler? {
        val variant = NestVariantRegistry.getById(nest.variantId) ?: return null
        return variant.getTypeData(nest.type).visualHandler
    }

    fun spawnVisuals(nest: NestData, debug: Boolean = false) {
        nest.pos.toLocation()?.let { getHandler(nest)?.placeVisuals(it, nest, debug) }
    }

    fun restoreVisuals(nest: NestData, debug: Boolean = false) {
        getHandler(nest)?.restoreTransientVisuals(nest, debug)
    }

    fun removeVisuals(nest: NestData, debug: Boolean = false) {
        getHandler(nest)?.cleanupVisuals(nest, debug)
    }

    fun updateVisuals(nest: NestData, state: NestState = nest.state, debug: Boolean = false) {
        getHandler(nest)?.updateVisualState(nest, state, debug)
    }

}