package com.glance.birds.nest.behavior.visual

import com.glance.birds.nest.Nest
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.data.NestState
import org.bukkit.Location

interface NestVisualHandler {
    fun placeVisuals(location: Location, nest: Nest, debug: Boolean = false)
    fun restoreTransientVisuals(nest: Nest, debug: Boolean = false)
    fun cleanupVisuals(nest: Nest, debug: Boolean = false)
    fun updateVisualState(nest: Nest, state: NestState = nest.state, debug: Boolean = false)
}