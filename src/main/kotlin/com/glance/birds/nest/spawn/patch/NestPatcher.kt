package com.glance.birds.nest.spawn.patch

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.spawn.NestSpawnConfig
import com.glance.birds.nest.spawn.NestSpawner
import com.glance.birds.nest.spawn.SpawnResult
import com.glance.birds.util.data.getPDC
import com.glance.birds.util.data.setPDC
import org.bukkit.Chunk
import org.bukkit.NamespacedKey

object NestPatcher {

    // todo const or cfg?
    private const val PATCH_VERSION = 1
    private val versionKey = NamespacedKey(BirdsExpansion.instance(), "nest_patch")

     fun patchChunk(chunk: Chunk): SpawnResult? {
         val current = chunk.getPDC<Int>(versionKey)
         if (current == PATCH_VERSION) return null

         // todo cfg actually from cfg - patch spawn?
         val result = NestSpawner.attemptSpawnNest(chunk, NestSpawnConfig(chance = 0.05))

         chunk.setPDC(versionKey, PATCH_VERSION)
         return result
     }

}