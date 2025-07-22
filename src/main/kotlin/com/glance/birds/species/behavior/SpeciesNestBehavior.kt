package com.glance.birds.species.behavior

import com.glance.birds.nest.data.NestData
import org.bukkit.entity.LivingEntity

interface SpeciesNestBehavior {
    fun onApproachNest(bird: LivingEntity, nest: NestData) {}
    fun onoUseNest(bird: LivingEntity, nest: NestData) {}
}