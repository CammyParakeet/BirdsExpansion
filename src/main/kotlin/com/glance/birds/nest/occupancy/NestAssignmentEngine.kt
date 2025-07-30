package com.glance.birds.nest.occupancy

import com.glance.birds.event.nest.block.NestPlaceEvent
import com.glance.birds.nest.behavior.mob.backoff.BackoffManager
import org.bukkit.World
import org.bukkit.entity.Mob
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

// TODO
object NestAssignmentEngine : Listener {

    private val backoff = BackoffManager()

    fun handleTick(world: World) {}

    fun tryAssignNest(mob: Mob) {}

    @EventHandler
    fun onNestPlaced(event: NestPlaceEvent) {}

}