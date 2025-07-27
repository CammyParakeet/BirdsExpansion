package com.glance.birds.nest.interaction

import com.glance.birds.nest.NestManager
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.util.world.WorldBlockPos
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object PlayerNestPlaceHandler {

    private val hooks = listOf(
        DefaultPlayerNestPlacer()
    )

    fun tryHandlePlacement(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        //if (event.isCancelled) return

        val item = event.item ?: return

        // TODO: proper registered hook
        val hook = hooks.firstOrNull { it.shouldHandle(item) } ?: return
        val loc = hook.getPlacementLocation(event) ?: return
        val variantId = hook.getVariantId(item) ?: return
        val variant = NestVariantRegistry.getById(variantId) ?: return

        val chunk = loc.chunk

        // TODO: get this from the item pdc
        val nest = NestData(
            pos = WorldBlockPos.fromLocation(loc),
            variantId = variantId,
            type = NestType.GROUND
        )

        NestManager.placeNest(chunk, nest)
        event.isCancelled = true
        event.player.swingMainHand() // todo proper hand
        item.amount -= 1
    }

}