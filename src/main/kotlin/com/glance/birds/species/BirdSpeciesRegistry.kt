package com.glance.birds.species

import com.glance.birds.species.vanilla.VanillaChickenSpecies
import com.glance.birds.species.vanilla.VanillaParrotSpecies
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType

// TODO: later support for custom registered birds
object BirdSpeciesRegistry {

    fun get(entity: Entity): BirdSpecies? {
        return when (entity.type) {
            EntityType.CHICKEN -> VanillaChickenSpecies()
            EntityType.PARROT -> VanillaParrotSpecies()
            else -> null
        }
    }

}