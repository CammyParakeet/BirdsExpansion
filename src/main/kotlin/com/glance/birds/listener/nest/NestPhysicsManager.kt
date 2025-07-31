package com.glance.birds.listener.nest

import com.glance.birds.event.nest.block.NestBreakEvent
import com.glance.birds.nest.Nest
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.util.world.blockCoords
import com.glance.birds.util.world.coords
import com.glance.birds.util.world.isNest
import com.glance.birds.util.world.leaves
import org.bukkit.Material
import org.bukkit.World
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
        val src = event.sourceBlock
        val aboveBlock = block.getRelative(BlockFace.UP)

        val changedType = event.changedType

        val blockIsNest = block.isNest()
        val srcIsNest = src.isNest()
        val aboveIsNest = aboveBlock.isNest()

        if (aboveIsNest) {
            event.isCancelled = true
            return
        }

        if (blockIsNest && block.getRelative(BlockFace.DOWN) == src) {
            if (src.type.isAir) {
                val nest = NestManager.getNestAt(block.location) ?: return
                if (NestManager.breakPlacedNest(nest)) {
                    NestBreakEvent(nest, NestBreakEvent.Reason.ENVIRONMENT).callEvent()
                }
            }
        }

//        if (blockIsNest && srcIsNest) {
//            println("Both block and src are the nest block at ${block.location.blockCoords()} - ${block.type} | ${src.type}")
//            event.isCancelled = true
//            return
//        }
//
//        if (blockIsNest) {
//            println("Physics nest block at ${block.location.blockCoords()} is ${block.type} |" +
//                    " src: ${src.type} at ${src.location.blockCoords()} | Changed type is $changedType")
//        }
//
//        if (srcIsNest) {
//            println("Physics nest src block at ${src.location.blockCoords()} is ${src.type} |" +
//                    " event block: ${block.type} at ${block.location.blockCoords()} | Changed type is $changedType")
//        }
//
//        if (aboveIsNest) {
//            println("ABOVE IS NEST FOR event block ${block.type} @${block.location.blockCoords()} |" +
//                    " src: ${src.type} @${src.location.blockCoords()} | NEST is ${aboveBlock.type} @${aboveBlock.location.blockCoords()}")
//            println("Above block changed blockdata is ${event.changedBlockData}")
//        }

        val nest = NestManager.getNestAt(block.location) ?: return
        val variant = nest.getVariant() ?: return

        val expectedBaseBlock = variant.getTypeData(nest.data.type).baseBlockType ?: return


        if (expectedBaseBlock != changedType) {
            // todo maybe figure out all surroundings??
            val below = block.location.world.getBlockAt(nest.location!!).getRelative(BlockFace.DOWN)

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

    private fun handleNestPhysics(event: BlockPhysicsEvent, nest: Nest) {
        val variant = nest.getVariant() ?: return

    }

    private fun shouldProtectFromPhysics(supportBlock: Block): Boolean {
        return supportBlock.type in protectedNestSurroundings
    }

}