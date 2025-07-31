package com.glance.birds.listener.nest

import com.glance.birds.event.nest.block.NestBreakEvent
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.util.world.leaves
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPhysicsEvent

object NestPhysicsManager : Listener {

    // todo full and configured?
    private val protectedNestSurroundings = leaves + setOf(Material.WATER)

    @EventHandler
    fun onBlockPhysics(event: BlockPhysicsEvent) {
        val block = event.block
        val nest = NestManager.getNestAt(block.location) ?: return
        val variant = nest.getVariant() ?: return

        val expectedBaseBlock = variant.getTypeData(nest.data.type).baseBlockType ?: return
        val changedType = event.changedType

        if (expectedBaseBlock != changedType) {
            // todo maybe figure out all surroundings??
            val below = block.getRelative(BlockFace.DOWN)

            if (shouldProtectFromPhysics(below)) {
                event.isCancelled = true
                return
            }

            // todo instead - allow cancel, check the recently broken, then allow
            if (NestManager.breakPlacedNest(nest)) {
                NestBreakEvent(nest, NestBreakEvent.Reason.ENVIRONMENT).callEvent()
            }
        }

        // todo other physics management
    }

    private fun shouldProtectFromPhysics(supportBlock: Block): Boolean {
        return supportBlock.type in protectedNestSurroundings
    }

}