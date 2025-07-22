package com.glance.birds.species

import com.glance.birds.nest.data.NestData

data class NestPreference(
    val nestTypeId: String,
    val weight: Int = 1,
    val condition: ((NestData) -> Boolean)? = null
)
