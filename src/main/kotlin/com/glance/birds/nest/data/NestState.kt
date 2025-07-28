package com.glance.birds.nest.data

import com.glance.birds.species.BirdSpeciesRegistry
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.Mob
import java.util.*

data class NestState(
    var eggCount: Int = 0,
    var featherCount: Int = 0,
    var isDamaged: Boolean = false,

    // Birds assigned to this nest (home)
    var assignedBirds: MutableSet<UUID> = mutableSetOf(),

    // Birds currently inside the nest or sitting visibly
    var residingBirds: MutableSet<UUID> = mutableSetOf()
) {
    val hasEggs: Boolean
        get() = eggCount > 0

    val hasFeathers: Boolean
        get() = featherCount > 0

    val isOccupied: Boolean
        get() = residingBirds.isNotEmpty()

    fun getCurrentOccupantSpace(): Int {
        return assignedBirds.sumOf { uuid ->
            val birdMob = Bukkit.getEntity(uuid)?: return@sumOf 0
            val species = BirdSpeciesRegistry.get(birdMob) ?: return@sumOf 0
            species.getNestSizeCost(birdMob)
        }
    }

    fun emptyContents() {
        eggCount = 0
        featherCount = 0
    }

    fun withEggs(eggs: Int) {
        eggCount = eggs
    }

    fun withFeathers(feathers: Int) {
        featherCount = feathers
    }

    fun addBird(bird: Entity) {
        this.assignedBirds.add(bird.uniqueId)
    }

    fun addResident(bird: Entity) {
        this.assignedBirds.add(bird.uniqueId)
        this.residingBirds.add(bird.uniqueId)
    }

    fun removeBird(bird: Entity) {
        this.assignedBirds.remove(bird.uniqueId)
        this.residingBirds.remove(bird.uniqueId)
    }

}
