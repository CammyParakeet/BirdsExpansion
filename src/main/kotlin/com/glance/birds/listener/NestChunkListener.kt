package com.glance.birds.listener

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.NestManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent

class NestChunkListener : Listener {

    private val plugin = BirdsExpansion.instance()

    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        NestManager.loadNestsForChunk(event.chunk)
    }

    @EventHandler
    fun onChunkUnload(event: ChunkUnloadEvent) {
        NestManager.unloadChunk(event.chunk)
    }

}