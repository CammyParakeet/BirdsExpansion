package com.glance.birds.listener

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.NestManager.getNestData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class NestInteractionListener : Listener {

    private val plugin = BirdsExpansion.instance()

    @EventHandler(ignoreCancelled = false)
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        val nest = block.getNestData() ?: return

        // todo block validation?

        event.isCancelled = true
        NestManager.removeNest(nest, drop = true)
    }

}