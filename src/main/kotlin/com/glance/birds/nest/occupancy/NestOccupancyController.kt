package com.glance.birds.nest.occupancy

import com.glance.birds.nest.Nest
import com.glance.birds.nest.behavior.NestTickHandler
import com.glance.birds.species.BirdSpeciesRegistry
import org.bukkit.Bukkit
import org.bukkit.entity.Mob
import java.util.UUID

class NestOccupancyController(val nest: Nest) : NestTickHandler {

    // todo: use mob here or bird wrapper?
    private val occupants = mutableMapOf<UUID, Mob>()

    override fun tick(nest: Nest) {
        val iterator = occupants.iterator()
        while (iterator.hasNext()) {
            val (uuid, birdMob) = iterator.next()

            if (!birdMob.isValid) {
                iterator.remove()
                removeMobId(uuid)
                continue
            }

            val species = BirdSpeciesRegistry.get(birdMob) ?: return

            // todo variant way of this?
            val behavior = species.preferredNestBehavior ?: return
            val isInNest = nest.state.residingBirds.contains(uuid)

            if (isInNest) {
                if (behavior.shouldExitNest(birdMob, nest)) {
                    nest.state.residingBirds.remove(birdMob.uniqueId)
                    behavior.onNestExited(birdMob, nest)
                }
            } else {
                if (behavior.shouldReturnToNest(birdMob, nest)) {
                    moveToNest(birdMob)
                }
            }

            behavior.onTick(birdMob, nest, isInNest)
        }
    }

    fun assignMob(mob: Mob) : OccupancyResult {
        if (occupants.containsKey(mob.uniqueId)) return OccupancyResult.AlreadyAssigned
        if (!canFitMob(mob)) return OccupancyResult.Full

        occupants[mob.uniqueId] = mob
        nest.state.assignedBirds.add(mob.uniqueId)
        return OccupancyResult.Assigned
    }

    fun removeMob(mob: Mob) {
        occupants.remove(mob.uniqueId)
        removeMobId(mob.uniqueId)
    }

    private fun removeMobId(mobUuid: UUID) {
        nest.state.assignedBirds.remove(mobUuid)
        nest.state.residingBirds.remove(mobUuid)
    }

    private fun moveToNest(mob: Mob) {
        val  target = nest.data.pos.toLocation() ?: return
        if (mob.location.distanceSquared(target) > (nest.getVariant()?.occupantEnterDistance ?: 1.0)) {
            mob.pathfinder.moveTo(target)
        } else {
            val species = BirdSpeciesRegistry.get(mob) ?: return
            species.preferredNestBehavior?.onNestEntered(mob, nest)
            nest.state.assignedBirds.add(mob.uniqueId)
            nest.state.residingBirds.add(mob.uniqueId)
        }
    }

    fun canFitMob(mob: Mob): Boolean {
        val species = BirdSpeciesRegistry.get(mob) ?: return false
        val maxSpace = nest.getVariant()?.maxOccupantSpace ?: 2
        val current = getCurrentOccupantSpace()
        val incoming = species.getNestSizeCost(mob)
        return current + incoming <= maxSpace
    }

    fun getCurrentOccupantSpace(): Int {
        return nest.state.assignedBirds.sumOf { uuid ->
            val birdMob = Bukkit.getEntity(uuid)?: return@sumOf 0
            val species = BirdSpeciesRegistry.get(birdMob) ?: return@sumOf 0
            species.getNestSizeCost(birdMob)
        }
    }

    fun getOccupants(): Collection<Mob> = occupants.values

}