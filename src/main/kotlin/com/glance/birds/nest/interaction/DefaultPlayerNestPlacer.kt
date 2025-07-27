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
        val clickedBlock = event.clickedBlock ?: return null
        if (clickedBlock.isReplaceable) return clickedBlock.location

        val face = event.blockFace

        val targetLoc = clickedBlock.getRelative(face).location
        val targetBlock = targetLoc.block

        return if (targetBlock.isReplaceable) {
            targetLoc
        } else {
            null
        }
    }

    override fun getVariantId(item: ItemStack): String? {
        return NestItemHandler.getVariantId(item)
    }
}