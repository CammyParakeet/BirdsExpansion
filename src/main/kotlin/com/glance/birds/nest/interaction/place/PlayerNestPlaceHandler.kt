package com.glance.birds.nest.interaction.place

import com.glance.birds.event.nest.block.NestPlaceEvent
import com.glance.birds.nest.Nest
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
        DefaultPlayerNestPlacer(),
        WaterNestPlacer()
    )

    fun tryHandlePlacement(event: PlayerInteractEvent): Boolean {
        val item = event.item ?: return false

        // TODO: proper registered hook
        // TODO: maybe more handle checks here?
        val hook = hooks.filter {
            it.shouldHandle(item)
            //it.canPlaceHere(item, event)
        }.maxByOrNull { it.priority } ?: return false

        val loc = hook.getPlacementLocation(event) ?: return false
        val variantId = hook.getVariantId(item) ?: return false
        val variant = NestVariantRegistry.getById(variantId) ?: return false
        // TODO: anything with the variant here?

        val chunk = loc.chunk
        val player = event.player

        // TODO: get this from the item pdc
        val nestData = NestData(
            pos = WorldBlockPos.fromLocation(loc),
            variantId = variantId,
            type = NestType.GROUND
        )
        val nest = Nest(nestData)

        event.isCancelled = true

        val placeEvent = NestPlaceEvent(nest, NestPlaceEvent.Cause.PLAYER, player)
        placeEvent.callEvent()
        if (placeEvent.isCancelled) return false

        when (player.gameMode) {
            GameMode.SURVIVAL -> {
                val hand = event.hand ?: EquipmentSlot.HAND
                val current = player.inventory.getItem(hand)

                if (current.type != Material.AIR && !current.isEmpty) {
                    current.amount -= 1
                }
            }
            GameMode.ADVENTURE, GameMode.SPECTATOR -> return false
            GameMode.CREATIVE -> {
                // TODO: permission?
            }
        }

        player.swingMainHand() // TODO: proper hand
        NestManager.placeNest(chunk, nest)

        return true
    }

}