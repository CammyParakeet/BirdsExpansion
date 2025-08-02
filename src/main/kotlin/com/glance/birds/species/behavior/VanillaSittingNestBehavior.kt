package com.glance.birds.species.behavior

import com.glance.birds.nest.Nest
import org.bukkit.entity.Animals
import org.bukkit.entity.Display
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.LivingEntity
import java.util.UUID

class VanillaSittingNestBehavior : SpeciesNestBehavior {

    private val markerMap = mutableMapOf<UUID, Display>()

    override fun onUseNest(bird: LivingEntity, nest: Nest) {
        // TODO
    }

    override fun onNestEntered(bird: LivingEntity, nest: Nest) {
        val world = bird.world

        // todo nest-data seat offset?
        val loc = nest.location
            ?.toCenterLocation()
            ?.subtract(0.0, 0.75, 0.0) ?: return

        val marker = world.spawn(loc, ItemDisplay::class.java) {
            it.isInvisible = true
            it.isPersistent = false
        }

        marker.addPassenger(bird)
        markerMap[nest.uniqueId] = marker

        // treat as using the nest
        onUseNest(bird, nest)
    }

    override fun onNestExited(bird: LivingEntity, nest: Nest) {
        markerMap[nest.uniqueId]?.let {
            it.removePassenger(bird)
            it.remove()
            markerMap.remove(nest.uniqueId)
        }

        bird.setAI(true)
        bird.isSilent = false

        val safeNestLoc = nest.location?.toCenterLocation() ?: return
        bird.teleport(safeNestLoc)
    }

    override fun shouldReturnToNest(entity: LivingEntity, nest: Nest): Boolean {
        // todo check occupancy? or generalize that?
        return entity.world.time > 12000 // default nighttime return
    }

    override fun isInPairMode(bird: LivingEntity): Boolean {
        return (bird as? Animals)?.isLoveMode == true
    }

    override fun clearPairMode(bird: LivingEntity) {
        (bird as? Animals)?.loveModeTicks = 0
    }

    override fun shouldExitNest(entity: LivingEntity, nest: Nest): Boolean {
        return entity.world.time in 0..1000
    }

}