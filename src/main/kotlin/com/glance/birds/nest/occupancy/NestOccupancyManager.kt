package com.glance.birds.nest.occupancy

import com.glance.birds.nest.Nest
import org.bukkit.entity.Mob

object NestOccupancyManager {

    fun assignToNest(mob: Mob, nest: Nest): OccupancyResult {
        return nest.occupancyController?.assignMob(mob) ?: OccupancyResult.NotInitialized
    }

    fun removeFromNest(mob: Mob, nest: Nest) {
        nest.occupancyController?.removeMob(mob)
    }

    fun getAssignedMobs(nest: Nest): Collection<Mob> {
        return nest.occupancyController?.getOccupants() ?: emptyList()
    }

    fun canFit(nest: Nest, mob: Mob): Boolean {
        return nest.occupancyController?.canFitMob(mob) == true
    }

}