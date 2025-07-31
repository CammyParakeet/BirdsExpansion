package com.glance.birds.util.world

import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import java.util.UUID

data class WorldBlockPos(val world: UUID, val x: Int, val y: Int, val z: Int) {
    companion object {
        fun fromBlock(block: Block): WorldBlockPos = fromLocation(block.location)
        fun fromLocation(loc: Location): WorldBlockPos =
            WorldBlockPos(loc.world.uid, loc.blockX, loc.blockY, loc.blockZ)
    }

    fun toLocation(): Location? {
        val world = Bukkit.getWorld(world) ?: return null
        return Location(world, x.toDouble(), y.toDouble(), z.toDouble())
    }

    val coords: String
        get() = "($x, $y, $z)"

    override fun toString(): String {
        val worldName = Bukkit.getWorld(world)?.name ?: world.toString()
        return "WorldBlockPos($worldName, $x, $y, $z)"
    }

}

fun World.getChunkIfLoaded(x: Int, z: Int): Chunk? {
    return if (isChunkLoaded(x, z)) {
        getChunkAt(x, z)
    } else null
}

fun World.getChunkIfLoaded(loc: Location): Chunk? = getChunkIfLoaded(loc.blockX shr 4, loc.blockZ shr 4)
fun World.getChunkIfLoaded(block: Block): Chunk? = getChunkIfLoaded(block.x shr 4, block.z shr 4)