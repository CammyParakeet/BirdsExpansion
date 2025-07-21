package com.glance.birds.listener

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.spawn.patch.NestPatcher
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent

class NestChunkListener : Listener {

    private val plugin = BirdsExpansion.instance()

    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        val chunk = event.chunk
        NestManager.loadNestsForChunk(chunk)
        NestPatcher.patchChunk(chunk)?.let {
            plugin.logger.info("Patching chunk $chunk with result: $it")
        }
    }

    @EventHandler
    fun onChunkUnload(event: ChunkUnloadEvent) {
        NestManager.unloadChunk(event.chunk)
    }

}