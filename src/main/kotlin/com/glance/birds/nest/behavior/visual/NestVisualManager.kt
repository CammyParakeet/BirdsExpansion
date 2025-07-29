package com.glance.birds.nest.behavior.visual

import com.glance.birds.nest.Nest
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.data.NestState
import com.glance.birds.nest.variant.NestVariantRegistry

object NestVisualManager {

    fun getHandler(nest: Nest): NestVisualHandler? {
        val variant = NestVariantRegistry.getById(nest.data.variantId) ?: return null
        return variant.getTypeData(nest.data.type).visualHandler
    }

    fun spawnVisuals(nest: Nest, debug: Boolean = false) {
        nest.location?.let { getHandler(nest)?.placeVisuals(it, nest, debug) }
    }

    fun restoreVisuals(nest: Nest, debug: Boolean = false) {
        getHandler(nest)?.restoreTransientVisuals(nest, debug)
    }

    fun removeVisuals(nest: Nest, debug: Boolean = false) {
        getHandler(nest)?.cleanupVisuals(nest, debug)
    }

    fun updateVisuals(nest: Nest, state: NestState = nest.state, debug: Boolean = false) {
        getHandler(nest)?.updateVisualState(nest, state, debug)
    }

}