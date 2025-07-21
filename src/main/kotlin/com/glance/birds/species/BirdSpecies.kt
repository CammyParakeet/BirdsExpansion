package com.glance.birds.species

import com.glance.birds.nest.NestBehavior
import com.glance.birds.nest.NestType

interface BirdSpecies {
    val id: String
    val displayName: String
    val preferredNestType: NestType
    val nestBehavior: NestBehavior
}