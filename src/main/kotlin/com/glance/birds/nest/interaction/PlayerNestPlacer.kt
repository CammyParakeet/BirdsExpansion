package com.glance.birds.nest.interaction

import org.bukkit.Location
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

interface PlayerNestPlacer {
    fun shouldHandle(item: ItemStack): Boolean
    fun getPlacementLocation(event: PlayerInteractEvent): Location?
    fun getVariantId(item: ItemStack): String?
}