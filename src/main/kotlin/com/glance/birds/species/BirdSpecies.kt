package com.glance.birds.species

import com.glance.birds.nest.behavior.NestBehavior
import net.kyori.adventure.text.Component

interface BirdSpecies {
    val id: String

    val displayNameRaw: String
    val displayNameRich: Component
        get() = Component.text(displayNameRaw)

    val nestPreferences: List<NestPreference>
    val preferredNestBehavior: NestBehavior?
}