package com.glance.birds.species.vanilla

import com.glance.birds.nest.behavior.NestBehavior
import com.glance.birds.species.BirdSpecies
import com.glance.birds.species.NestPreference

class VanillaChickenSpecies : BirdSpecies {
    override val id: String = "vanilla_chicken"
    override val displayNameRaw: String = "Chicken"

    override val nestPreferences: List<NestPreference> = listOf(
        NestPreference("ground", weight = 100)
    )

    override val preferredNestBehavior: NestBehavior?
        get() = TODO("Not yet implemented")

}