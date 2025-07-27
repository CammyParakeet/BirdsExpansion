package com.glance.birds.listener

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.NestManager.getNestData
import com.glance.birds.nest.interaction.PlayerNestPlaceHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent

class NestInteractionListener : Listener {

    private val plugin = BirdsExpansion.instance()

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block

        val nest = block.getNestData() ?: return

        // todo block validation?

        event.isCancelled = true
        NestManager.removeNest(nest, drop = true)
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onRightClick(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        PlayerNestPlaceHandler.tryHandlePlacement(event)
    }

}