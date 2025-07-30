package com.glance.birds.nest

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.behavior.sound.playBreakSound
import com.glance.birds.nest.behavior.sound.playPlaceSound
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.nest.behavior.visual.NestVisualManager
import com.glance.birds.nest.data.NestState
import com.glance.birds.nest.occupancy.NestOccupancyController
import com.glance.birds.util.data.getPDC
import com.glance.birds.util.data.removePDC
import com.glance.birds.util.data.setPDC
import com.glance.birds.util.task.runSync
import com.glance.birds.util.world.getChunkIfLoaded
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import java.util.concurrent.ConcurrentHashMap

object NestManager {

    private val plugin: BirdsExpansion = BirdsExpansion.instance()
    private val gson = Gson()
    private val nestsByChunk = ConcurrentHashMap<Chunk, MutableList<Nest>>()
    private val chunkKey = NamespacedKey(plugin, "nests")

    fun flushChunk(chunk: Chunk) {
        runSync {
            unloadChunk(chunk)
            chunk.world.unloadChunk(chunk)

            chunk.world.loadChunk(chunk)
            loadNestsForChunk(chunk, true)
        }
    }

    fun loadNestsForChunk(chunk: Chunk, loadVisuals: Boolean = false): MutableList<Nest>? {
        val jsonString = chunk.getPDC<String>(chunkKey) ?: return null
        val nestJsonArray = JsonParser.parseString(jsonString).asJsonArray
        val nests = mutableListOf<Nest>()

        for (element in nestJsonArray) {
            val obj = element.asJsonObject
            val data = gson.fromJson(obj["data"], NestData::class.java)
            val state = gson.fromJson(obj["state"], NestState::class.java)

            val nest = Nest(data, state)

            // todo nest init config
            initializeNest(nest, null, loadVisuals)

            nests.add(nest)
        }

        nestsByChunk[chunk] = nests

        return nests
    }

    // todo nest init config
    private fun initializeNest(nest: Nest, config: Any? = null, loadVisuals: Boolean = false) {
        val controller = NestOccupancyController(nest)
        nest.occupancyController = controller

        if (loadVisuals) NestVisualManager.restoreVisuals(nest)
    }

    fun getLoadedNests(): List<Nest> {
        return this.nestsByChunk.values.flatten()
    }

    fun getNestsInLoadedChunk(chunk: Chunk): List<Nest> {
        return nestsByChunk[chunk].orEmpty()
    }

    fun getNestChunks(): Set<Chunk> {
        return this.nestsByChunk.keys
    }

    fun saveNestsForChunk(chunk: Chunk) {
        val nests = nestsByChunk[chunk] ?: return
        val nestJsonArray = JsonArray()

        nests.forEach { nest ->
            val fullNestJson = JsonObject()

            val dataJson = gson.toJsonTree(nest.data)
            val stateJson = gson.toJsonTree(nest.state)

            fullNestJson.add("data", dataJson)
            fullNestJson.add("state", stateJson)

            nestJsonArray.add(fullNestJson)
        }

        val jsonString = gson.toJson(nestJsonArray)
        chunk.setPDC(chunkKey, jsonString)
    }

    fun unloadChunk(chunk: Chunk) {
        saveNestsForChunk(chunk)
        nestsByChunk.remove(chunk)
    }

    fun placeNest(chunk: Chunk, nest: Nest, debug: Boolean = false) {
        // todo this kind of initialization should be centralized somewhere with config in mind
        val controller = NestOccupancyController(nest)
        nest.occupancyController = controller

        runSync {
            addNest(chunk, nest)
            NestVisualManager.spawnVisuals(nest, debug)
            nest.playPlaceSound()
            saveNestsForChunk(chunk)
        }
    }

    fun addNest(chunk: Chunk, nest: Nest) {
        nestsByChunk.computeIfAbsent(chunk) { mutableListOf() }.add(nest)
    }

    fun getNestsInChunk(chunk: Chunk): List<Nest> {
        return nestsByChunk.getOrPut(chunk) {
            val loaded = loadNestsForChunk(chunk, false)
            loaded ?: mutableListOf()
        }
    }

    fun getNestAt(location: Location): Nest? {
        val chunk = location.chunk
        return getNestsInChunk(chunk).firstOrNull { nest ->
            nest.location?.toBlockLocation() == location.toBlockLocation()
        }
    }

    fun Block.getNestData(): Nest? {
        return getNestAt(this.location)
    }

    fun getNearbyNests(center: Location, radius: Int): List<Nest> {
        val world = center.world ?: return emptyList()
        val chunkRadius = (radius / 16) + 1
        val cx = center.chunk.x
        val cz = center.chunk.z

        val nearby = mutableListOf<Nest>()
        for (dx in -chunkRadius..chunkRadius) {
            for (dz in -chunkRadius..chunkRadius) {
                val chunk = world.getChunkIfLoaded(cx + dx, cz + dz) ?: continue
                nearby += getNestsInChunk(chunk).filter {
                    (it.location?.distance(center) ?: Double.MAX_VALUE) <= radius
                }
            }
        }

        return nearby
    }

    fun getNearestNest(center: Location, radius: Int): Nest? {
        return getNearbyNests(center, radius).minByOrNull { d ->
            d.location?.distanceSquared(center) ?: Double.MAX_VALUE }
    }

    fun breakPlacedNest(nest: Nest, drop: Boolean = true): Boolean {
        val location = nest.location ?: return false
        val chunk = location.chunk

        nest.playBreakSound()
        NestVisualManager.removeVisuals(nest)

        val list = nestsByChunk[chunk] ?: return false
        list.remove(nest)

        if (drop) {
            val variant = NestVariantRegistry.get(nest)
            val dropItem = variant?.getTypeData(nest.data.type)?.dropItem
            if (dropItem != null) {
                location.world.dropItemNaturally(location.toCenterLocation(), dropItem)
            }
        }

        saveNestsForChunk(chunk)

        return true
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