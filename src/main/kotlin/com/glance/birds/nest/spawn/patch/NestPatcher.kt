package com.glance.birds.nest.spawn.patch

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.contents.NestDropMode
import com.glance.birds.nest.spawn.NestSpawnConfig
import com.glance.birds.nest.spawn.NestSpawner
import com.glance.birds.nest.spawn.SpawnResult
import com.glance.birds.nest.variant.NestVariantRegistry
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

         val existing = NestManager.loadNestsForChunk(chunk, false) ?: emptyList()
         val validNests = existing.filter { nest ->
             nest.data.variantId != null &&
             nest.data.variantId.isNotBlank() &&
             nest.data.dropMode != null &&
             NestVariantRegistry.getById(nest.data.variantId) != null
         }

         if (validNests.size != existing.size) {
             NestManager.clearChunk(chunk)
             validNests.forEach { NestManager.placeNest(chunk, it) }
         }

         // todo spawn cfg actually from cfg - specific to patching
         if (current != PATCH_VERSION) {
             val result = NestSpawner.attemptSpawnNest(chunk, NestSpawnConfig(chance = 0.5))
             chunk.setPDC(versionKey, PATCH_VERSION)

             return result
         }

         return null
     }

}


fun NestData.patch() {
    if (this.dropMode == null) {
        this.dropMode = NestDropMode.SURVIVAL_ONLY
    }

//    if (this.tickHandlers == null) {
//        this.tickHandlers = mutableSetOf()
//    }
//
//    return if (this.state == null) {
//        this.copy(state = NestState())
//    } else {
//        this
//    }
}