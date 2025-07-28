package com.glance.birds.nest.occupancy

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.data.NestData
import com.glance.birds.species.BirdSpeciesRegistry
import org.bukkit.entity.Mob
import org.bukkit.plugin.Plugin
import java.util.UUID

class NestOccupancyController(
    val nest: NestData,
    private val plugin: Plugin = BirdsExpansion.instance()
) {

    // todo: use mob here or bird wrapper?
    private val occupants = mutableMapOf<UUID, Mob>()

    fun tick() {
        val iterator = occupants.iterator()
        while (iterator.hasNext()) {
            val (uuid, birdMob) = iterator.next()

            if (!birdMob.isValid) {
                iterator.remove()
                nest.state.removeBird(birdMob)
                continue
            }

            val species = BirdSpeciesRegistry.get(birdMob) ?: return

            // todo variant way of this?
            val behavior = species.preferredNestBehavior ?: return

            if (behavior.shouldReturnToNest(birdMob, nest)) {
                moveToNest(birdMob)
            }

            behavior.onTick(birdMob, nest)
        }
    }

    fun assignMob(mob: Mob) : OccupancyResult {
        if (occupants.containsKey(mob.uniqueId)) return OccupancyResult.AlreadyAssigned
        if (!nest.canFitMob(mob)) return OccupancyResult.Full

        occupants[mob.uniqueId] = mob
        nest.state.addBird(mob)
        return OccupancyResult.Assigned
    }

    fun removeMob(mob: Mob) {
        occupants.remove(mob.uniqueId)
        nest.state.removeBird(mob)
    }

    private fun moveToNest(mob: Mob) {
        val  target = nest.pos.toLocation() ?: return
        if (mob.location.distanceSquared(target) > (nest.getVariant()?.occupantEnterDistance ?: 1.0)) {
            mob.pathfinder.moveTo(target)
        } else {
            val species = BirdSpeciesRegistry.get(mob) ?: return
            species.preferredNestBehavior?.onNestEntered(mob, nest)
            nest.state.addResident(mob)
        }
    }

    fun getOccupants(): Collection<Mob> = occupants.values

}