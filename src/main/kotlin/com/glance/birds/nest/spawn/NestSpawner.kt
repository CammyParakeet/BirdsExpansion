package com.glance.birds.nest.spawn

import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.behavior.visual.NestVisualManager
import com.glance.birds.util.world.WorldBlockPos
import org.bukkit.Chunk
import org.bukkit.HeightMap
import org.bukkit.Material
import kotlin.random.Random

object NestSpawner {

    fun attemptSpawnNest(chunk: Chunk, config: NestSpawnConfig): SpawnResult {
        if (Random.nextDouble() > config.chance) {
            return SpawnResult.SkippedDueToChance
        }

        val world = chunk.world
        val baseX = chunk.x shl 4
        val baseZ = chunk.z shl 4

        var result: SpawnResult = SpawnResult.NoValidLocationFound
        repeat(config.validLocationAttempts) {
            val x = baseX + Random.nextInt(16)
            val z = baseZ + Random.nextInt(16)
            val block = world.getHighestBlockAt(x, z, HeightMap.MOTION_BLOCKING)

            val biome = block.biome
            if (config.biomeWhiteList != null && biome !in config.biomeWhiteList) {
                result = SpawnResult.SkippedDueToBiome
                return@repeat
            }

            // TODO: block checks
            //if (incorrect block) result = SpawnResult.NoValidLocationFound

            // TODO: proper impl
            // e.g. tree nests would need a threshold of leaf blocks in a grid around the highest point found
            // with tree heightmap
            val type = when (block.type) {
                Material.GRASS_BLOCK, Material.DIRT -> NestType.GROUND
                Material.OAK_LEAVES -> NestType.TREE
                else -> NestType.TREE
            }

            val nearby = NestManager.getNearbyNests(block.location, config.nearbyNestsRadius)
            if (config.maxNearbyNests != null && nearby.size >= config.maxNearbyNests) {
                return SpawnResult.SkippedDueToDensity
            }

            val nest = NestData(
                pos = WorldBlockPos.fromLocation(block.location),
                variantId = "", // TODO
                type = type,
                metadata = mapOf("" to "") // TODO
            )
            
            NestManager.placeNest(chunk, nest)

            return SpawnResult.Spawned(nest)
        }

        return result
    }

}