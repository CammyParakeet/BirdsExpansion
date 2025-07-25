package com.glance.birds.nest.visual

import com.glance.birds.nest.data.NestData
import org.bukkit.Location

interface NestVisualHandler {
    fun placeVisuals(location: Location, nestData: NestData, debug: Boolean = false)
    fun restoreTransientVisuals(nestData: NestData, debug: Boolean = false)
    fun cleanupVisuals(nestData: NestData, debug: Boolean = false)
    fun updateVisualState(nestData: NestData, state: NestVisualState, debug: Boolean = false)
}