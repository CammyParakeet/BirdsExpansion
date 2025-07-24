package com.glance.birds.nest

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.nest.visual.NestVisualManager
import com.glance.birds.util.data.getPDC
import com.glance.birds.util.data.removePDC
import com.glance.birds.util.data.setPDC
import com.glance.birds.util.task.runSync
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import java.util.concurrent.ConcurrentHashMap

object NestManager {

    private val plugin: BirdsExpansion = BirdsExpansion.instance()
    private val gson = Gson()
    private val nestsByChunk = ConcurrentHashMap<Chunk, MutableList<NestData>>()
    private val chunkKey = NamespacedKey(plugin, "nests")

    fun flushChunk(chunk: Chunk) {
        runSync {
            unloadChunk(chunk)
            chunk.world.unloadChunk(chunk)

            chunk.world.loadChunk(chunk)
            loadNestsForChunk(chunk, true)
        }
    }

    fun loadNestsForChunk(chunk: Chunk, loadVisuals: Boolean = false): MutableList<NestData>? {
        val json = chunk.getPDC<String>(chunkKey) ?: return null

        val type = object : TypeToken<List<NestData>>(){}.type
        val nests: List<NestData> = gson.fromJson(json, type)

        val nestsAsMutable = nests.toMutableList()
        nestsByChunk[chunk] = nestsAsMutable

        if (loadVisuals) {
            nestsAsMutable.forEach { NestVisualManager.restoreVisuals(it) }
        }

        return nestsAsMutable
    }

    fun saveNestsForChunk(chunk: Chunk) {
        val nests = nestsByChunk[chunk] ?: return
        val json = gson.toJson(nests)

        chunk.setPDC(chunkKey, json)
    }

    fun unloadChunk(chunk: Chunk) {
        saveNestsForChunk(chunk)
        nestsByChunk.remove(chunk)
    }

    fun placeNest(chunk: Chunk, nest: NestData) {
        addNest(chunk, nest)
        NestVisualManager.spawnVisuals(nest)
        saveNestsForChunk(chunk)
    }

    fun addNest(chunk: Chunk, nest: NestData) {
        nestsByChunk.computeIfAbsent(chunk) { mutableListOf() }.add(nest)
    }

    fun getNestsInChunk(chunk: Chunk): List<NestData> {
        return nestsByChunk.getOrPut(chunk) {
            val loaded = loadNestsForChunk(chunk, false)
            loaded ?: mutableListOf()
        }
    }

    fun getNestAt(location: Location): NestData? {
        val chunk = location.chunk
        return getNestsInChunk(chunk).firstOrNull { nest ->
            nest.pos.toLocation()?.toBlockLocation() == location.toBlockLocation()
        }
    }

    fun Block.getNestData(): NestData? {
        return getNestAt(this.location)
    }

    fun getNearbyNests(center: Location, radius: Int): List<NestData> {
        val world = center.world ?: return emptyList()
        val chunkRadius = (radius / 16) + 1
        val cx = center.chunk.x
        val cz = center.chunk.z

        val nearby = mutableListOf<NestData>()
        for (dx in -chunkRadius..chunkRadius) {
            for (dz in -chunkRadius..chunkRadius) {
                val chunk = world.getChunkAt(cx + dx, cz + dz)
                nearby += getNestsInChunk(chunk).filter {
                    (it.pos.toLocation()?.distance(center) ?: Double.MAX_VALUE) <= radius
                }
            }
        }

        return nearby
    }

    fun removeNest(nest: NestData, drop: Boolean = true) {
        val location = nest.pos.toLocation() ?: return
        val chunk = location.chunk

        NestVisualManager.removeVisuals(nest)

        val list = nestsByChunk[chunk] ?: return
        list.remove(nest)

        if (drop) {
            val variant = NestVariantRegistry.getById(nest.variantId)
            val dropItem = variant?.getTypeData(nest.type)?.dropItem
            if (dropItem != null) {
                location.world.dropItemNaturally(location, dropItem)
            }
        }

        saveNestsForChunk(chunk)
    }

    fun clearChunk(chunk: Chunk) {
        nestsByChunk.remove(chunk)
        chunk.removePDC(chunkKey)
    }

    fun shutdown() {
        for ((chunk, _) in nestsByChunk) {
            saveNestsForChunk(chunk)
        }
        nestsByChunk.clear()
    }

}