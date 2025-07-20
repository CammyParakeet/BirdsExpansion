package com.glance.birds.util.world

import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.UUID

data class WorldBlockPos(val world: UUID, val x: Int, val y: Int, val z: Int) {
    companion object {
        fun fromLocation(loc: Location): WorldBlockPos =
            WorldBlockPos(loc.world.uid, loc.blockX, loc.blockY, loc.blockZ)
    }

    fun toLocation(): Location? {
        val world = Bukkit.getWorld(world) ?: return null
        return Location(world, x.toDouble(), y.toDouble(), z.toDouble())
    }

    override fun toString(): String {
        val worldName = Bukkit.getWorld(world)?.name ?: world.toString()
        return "WorldBlockPos($worldName, $x, $y, $z)"
    }

}