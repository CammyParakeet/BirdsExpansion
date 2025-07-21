package com.glance.birds.nest

import com.glance.birds.nest.spawn.placement.NestPlacer
import org.bukkit.Location

interface NestBehavior {
    fun getPlacer(): NestPlacer
    fun spawnBird(location: Location)
}