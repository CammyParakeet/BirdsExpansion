package com.glance.birds.nest.interaction

import com.glance.birds.nest.item.NestItemHandler
import com.glance.birds.nest.item.NestItemHandler.isNestItem
import org.bukkit.Location
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class DefaultPlayerNestPlacer : PlayerNestPlacer {
    override fun shouldHandle(item: ItemStack): Boolean {
        return item.isNestItem()
    }

    override fun getPlacementLocation(event: PlayerInteractEvent): Location? {
        return event.clickedBlock?.getRelative(event.blockFace)?.location
    }

    override fun getVariantId(item: ItemStack): String? {
        return NestItemHandler.getVariantId(item)
    }
}