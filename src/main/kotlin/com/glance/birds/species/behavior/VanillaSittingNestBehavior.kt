package com.glance.birds.species.behavior

import com.glance.birds.nest.data.NestData
import org.bukkit.entity.Display
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.LivingEntity
import java.util.UUID

class VanillaSittingNestBehavior : SpeciesNestBehavior {

    private val markerMap = mutableMapOf<UUID, Display>()

    override fun onUseNest(bird: LivingEntity, nest: NestData) {
        // TODO
    }

    override fun onNestEntered(bird: LivingEntity, nest: NestData) {
        val world = bird.world
        val loc = nest.pos.toLocation()
            ?.toCenterLocation()
            ?.subtract(0.0, 0.4, 0.0) ?: return

        val marker = world.spawn(loc, ItemDisplay::class.java) {
            it.isInvisible = true
            it.isPersistent = false
        }

        marker.addPassenger(bird)
        markerMap[nest.id] = marker

        // treat as using the nest
        onUseNest(bird, nest)
    }

    override fun onNestExited(bird: LivingEntity, nest: NestData) {
        markerMap[nest.id]?.let {
            it.remove()
            markerMap.remove(nest.id)
        }

        bird.setAI(true)
        bird.isSilent = false
    }

    override fun shouldReturnToNest(entity: LivingEntity, nestData: NestData): Boolean {
        // todo check occupancy? or generalize that?
        return entity.world.time > 12000 // default nighttime return
    }

}