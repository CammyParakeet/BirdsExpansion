package com.glance.birds.listener.nest

import com.glance.birds.BirdsExpansion
import com.glance.birds.event.nest.block.NestBreakEvent
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.NestManager.getNestData
import com.glance.birds.nest.contents.NestContentsHandler
import com.glance.birds.nest.data.NestDropMode
import com.glance.birds.nest.interaction.BaseNestInteractionHandler
import com.glance.birds.nest.interaction.place.PlayerNestPlaceHandler
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.util.task.Debouncer
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import java.util.UUID

class NestInteractionListener : Listener {

    private val plugin = BirdsExpansion.instance()

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block

        val nest = block.getNestData() ?: return

        // todo block validation?

        event.isCancelled = true

        val breakEvent = NestBreakEvent(nest, NestBreakEvent.Reason.PLAYER, event.player)
        breakEvent.callEvent()
        if (breakEvent.isCancelled) return

        if (NestManager.breakPlacedNest(nest, drop = true)) {
            when (nest.data.dropMode) {
                NestDropMode.ALWAYS -> NestContentsHandler.dropAll(nest)
                NestDropMode.SURVIVAL_ONLY -> {
                    if (event.player.gameMode == GameMode.SURVIVAL) {
                        NestContentsHandler.dropAll(nest)
                    }
                }
                else -> {}
            }


        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onRightClick(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        if (PlayerNestPlaceHandler.tryHandlePlacement(event)) return

        if (!NestInteractionDebouncer.canUse(event.player.uniqueId)) return

        val block = event.clickedBlock ?: return
        val nest = block.getNestData() ?: return

        val variant = NestVariantRegistry.get(nest)
        //val handled = variant?.getTypeData(nest.type)?. TODO interaction handler??
        val handled = false

        if (handled != true &&
            BaseNestInteractionHandler.handleInteraction(nest, event.player))
        {
            event.isCancelled = true
        }
    }

}

object NestInteractionDebouncer : Debouncer<UUID>(cooldownMs = 100L)