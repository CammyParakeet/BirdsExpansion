package com.glance.birds.species.vanilla

import com.glance.birds.species.BirdSpecies
import com.glance.birds.species.NestPreference
import com.glance.birds.species.behavior.VanillaSittingNestBehavior
import com.glance.birds.species.behavior.SpeciesNestBehavior

object VanillaParrotSpecies : BirdSpecies {
    override val id: String = "vanilla_parrot"
    override val displayNameRaw: String = "Parrot"

    override val nestPreferences: List<NestPreference> = listOf(
        NestPreference("tree", weight = 100)
    )

    override val preferredNestBehavior: SpeciesNestBehavior = VanillaSittingNestBehavior()

}