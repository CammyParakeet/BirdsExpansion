package com.glance.birds.nest.interaction

import com.glance.birds.nest.NestManager
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.util.world.WorldBlockPos
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

object PlayerNestPlaceHandler {

    private val hooks = listOf(
        DefaultPlayerNestPlacer()
    )

    fun tryHandlePlacement(event: PlayerInteractEvent) {
        val item = event.item ?: return

        // TODO: proper registered hook
        val hook = hooks.firstOrNull { it.shouldHandle(item) } ?: return
        val loc = hook.getPlacementLocation(event) ?: return
        val variantId = hook.getVariantId(item) ?: return
        val variant = NestVariantRegistry.getById(variantId) ?: return
        // TODO: anything with the variant here?

        val chunk = loc.chunk
        val player = event.player

        // TODO: get this from the item pdc
        val nest = NestData(
            pos = WorldBlockPos.fromLocation(loc),
            variantId = variantId,
            type = NestType.GROUND
        )

        event.isCancelled = true

        when (player.gameMode) {
            GameMode.SURVIVAL -> {
                val hand = event.hand ?: EquipmentSlot.HAND
                val current = player.inventory.getItem(hand)

                if (current.type != Material.AIR && !current.isEmpty) {
                    current.amount -= 1
                }
            }
            GameMode.ADVENTURE, GameMode.SPECTATOR -> return
            GameMode.CREATIVE -> {
                // TODO: permission?
            }
        }

        player.swingMainHand() // TODO: proper hand
        NestManager.placeNest(chunk, nest)
    }

}