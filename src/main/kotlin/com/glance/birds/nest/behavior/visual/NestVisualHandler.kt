package com.glance.birds.nest.behavior.visual

import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.data.NestState
import org.bukkit.Location

interface NestVisualHandler {
    fun placeVisuals(location: Location, nestData: NestData, debug: Boolean = false)
    fun restoreTransientVisuals(nestData: NestData, debug: Boolean = false)
    fun cleanupVisuals(nestData: NestData, debug: Boolean = false)
    fun updateVisualState(nestData: NestData, state: NestState = nestData.state, debug: Boolean = false)
}