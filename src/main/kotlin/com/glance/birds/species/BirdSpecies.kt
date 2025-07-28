package com.glance.birds.species

import com.glance.birds.species.behavior.SpeciesNestBehavior
import net.kyori.adventure.text.Component
import org.bukkit.entity.Ageable
import org.bukkit.entity.Entity

interface BirdSpecies {
    val id: String

    val displayNameRaw: String
    val displayNameRich: Component
        get() = Component.text(displayNameRaw)

    val nestPreferences: List<NestPreference>
    val preferredNestBehavior: SpeciesNestBehavior?

    fun getNestSizeCost(entity: Entity): Int {
        return if (entity is Ageable && !entity.isAdult) 1 else 2
    }

}