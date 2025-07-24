package com.glance.birds.nest.visual

import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.variant.NestVariantRegistry

object NestVisualManager {

    fun getHandler(nest: NestData): NestVisualHandler? {
        val variant = NestVariantRegistry.getById(nest.variantId) ?: return null
        return variant.getTypeData(nest.type).visualHandler
    }

    fun spawnVisuals(nest: NestData) {
        nest.pos.toLocation()?.let { getHandler(nest)?.placeVisuals(it, nest) }
    }

    fun restoreVisuals(nest: NestData) {
        getHandler(nest)?.restoreTransientVisuals(nest)
    }

    fun removeVisuals(nest: NestData) {
        getHandler(nest)?.cleanupVisuals(nest)
    }

    fun updateVisuals(nest: NestData, state: NestVisualState) {
        getHandler(nest)?.updateVisualState(nest, state)
    }

}