package com.glance.birds.nest.visual

import com.glance.birds.nest.data.NestData
import org.bukkit.Location

interface NestVisualHandler {
    fun placeVisuals(location: Location, nestData: NestData)
    fun restoreTransientVisuals(nestData: NestData)
    fun cleanupVisuals(nestData: NestData)
    fun updateVisualState(nestData: NestData, state: NestVisualState)
}