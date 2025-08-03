package com.glance.birds.nest.interaction.place

import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.item.NestItemHandler
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Levelled
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class WaterNestPlacer : PlayerNestPlacer {

    override val priority: Int
        get() = 1

    // todo shorthand?
    override fun shouldHandle(item: ItemStack): Boolean {
        return NestItemHandler.getVariant(item)?.supportedTypes?.contains(NestType.WATER) == true
    }

    override fun getPlacementLocation(event: PlayerInteractEvent): Location? {
        val player = event.player

        // TODO: get proper place range
        val hitWater = rayCastWater(player, 4.0) ?: return null

        val level = (hitWater.blockData as Levelled).level
        if (level != 0) return null

        val above = hitWater.getRelative(BlockFace.UP)
        if (!above.isReplaceable) return null

        return above.location
    }

    private fun rayCastWater(player: Player, maxDistance: Double = 4.0): Block? {
        val eyeLoc = player.eyeLocation
        val dir = eyeLoc.direction.normalize()
        val world = player.world

        val current = eyeLoc.clone()
        for (i in 0 until (maxDistance * 10).toInt()) {
            current.add(dir.clone().multiply(0.1))

            val block = current.block
            if (block.type == Material.WATER && block.blockData is Levelled) {
                return block
            }

            if (block.type.isSolid) break
        }

        return null
    }

    override fun getVariantId(item: ItemStack): String? {
        return NestItemHandler.getVariantId(item)
    }
}