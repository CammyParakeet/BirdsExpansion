package com.glance.birds.nest.spawn

import com.glance.birds.nest.NestData
import org.bukkit.block.Biome

data class NestSpawnConfig(
    val chance: Double,
    val validLocationAttempts: Int = 8,
    val minDistanceFromNest: Int = 8,
    val biomeWhiteList: Set<Biome>? = null,
    val maxNearbyNests: Int? = null,
    val nearbyNestsRadius: Int = 9
)

sealed class SpawnResult {
    data class Spawned(val nest: NestData): SpawnResult()
    data object SkippedDueToChance : SpawnResult()
    data object SkippedDueToBiome : SpawnResult()
    data object SkippedDueToDensity : SpawnResult()
    data object NoValidLocationFound : SpawnResult()
}
