package com.glance.birds.nest.interaction

import org.bukkit.Location
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class IAPlayerNestPlacer : PlayerNestPlacer {
    override fun shouldHandle(item: ItemStack): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPlacementLocation(event: PlayerInteractEvent): Location? {
        TODO("Not yet implemented")
    }

    override fun getVariantId(item: ItemStack): String? {
        TODO("Not yet implemented")
    }
}