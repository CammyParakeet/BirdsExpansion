package com.glance.birds.nest.visual

import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.variant.NestVariantRegistry

object NestVisualManager {

    fun spawnVisuals(nest: NestData) {
        val variant = NestVariantRegistry.getById(nest.variantId) ?: return
        val typeData = variant.getTypeData(nest.type)

        nest.pos.toLocation()?.let { typeData.visualHandler.placeVisuals(it, nest) }
    }

    fun removeVisuals(nest: NestData) {
        val variant = NestVariantRegistry.getById(nest.variantId) ?: return
        val typeData = variant.getTypeData(nest.type)

        typeData.visualHandler.cleanupVisuals(nest)
    }

    fun updateVisuals(nest: NestData, state: NestVisualState) {
        val variant = NestVariantRegistry.getById(nest.variantId) ?: return
        val typeData = variant.getTypeData(nest.type)

        typeData.visualHandler.updateVisualState(nest, state)
    }

}