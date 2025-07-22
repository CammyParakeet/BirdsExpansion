package com.glance.birds.species.vanilla

import com.glance.birds.nest.behavior.NestBehavior
import com.glance.birds.species.BirdSpecies
import com.glance.birds.species.NestPreference

class VanillaParrotSpecies : BirdSpecies {
    override val id: String = "vanilla_parrot"
    override val displayNameRaw: String = "Parrot"

    override val nestPreferences: List<NestPreference> = listOf(
        NestPreference("tree", weight = 100)
    )

    override val preferredNestBehavior: NestBehavior?
        get() = TODO("Not yet implemented")

}