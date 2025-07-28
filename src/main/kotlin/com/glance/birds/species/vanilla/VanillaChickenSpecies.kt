package com.glance.birds.species.vanilla

import com.glance.birds.species.BirdSpecies
import com.glance.birds.species.NestPreference
import com.glance.birds.species.behavior.VanillaSittingNestBehavior
import com.glance.birds.species.behavior.SpeciesNestBehavior

class VanillaChickenSpecies : BirdSpecies {
    override val id: String = "vanilla_chicken"
    override val displayNameRaw: String = "Chicken"

    override val nestPreferences: List<NestPreference> = listOf(
        NestPreference("ground", weight = 100)
    )

    override val preferredNestBehavior: SpeciesNestBehavior = VanillaSittingNestBehavior()

}