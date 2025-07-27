package com.glance.birds.listener

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.NestManager.getNestData
import com.glance.birds.nest.interaction.PlayerNestPlaceHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent

class NestInteractionListener : Listener {

    private val plugin = BirdsExpansion.instance()

    @EventHandler(ignoreCancelled = false)
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block

        println("Block broken ${block.type} at ${block.location.toBlockLocation()}")

        val nest = block.getNestData() ?: run {
            println("No nest data found")
            return
        }

        // todo block validation?

        event.isCancelled = true
        NestManager.removeNest(nest, drop = true)
    }

    @EventHandler(ignoreCancelled = false)
    fun onRightClick(event: PlayerInteractEvent) {
        //if (event.action != Action.RIGHT_CLICK_BLOCK) return
        PlayerNestPlaceHandler.tryHandlePlacement(event)
    }

}