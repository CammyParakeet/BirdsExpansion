package com.glance.birds.nest.interaction.place

import com.glance.birds.nest.data.type.NestType
import org.bukkit.Location
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

interface PlayerNestPlacer {
    val priority: Int get() = 0
    fun shouldHandle(item: ItemStack): Boolean
    fun canPlaceHere(item: ItemStack, event: PlayerInteractEvent): Boolean = true
    fun getPlacementLocation(event: PlayerInteractEvent): Location?
    fun getVariantId(item: ItemStack): String?
    fun getSupportedTypes(item: ItemStack): Set<NestType> = setOf(NestType.GROUND)
}