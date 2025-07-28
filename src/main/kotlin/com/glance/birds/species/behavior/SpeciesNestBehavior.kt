package com.glance.birds.species.behavior

import com.glance.birds.nest.data.NestData
import org.bukkit.entity.LivingEntity

interface SpeciesNestBehavior {
    fun onApproachNest(bird: LivingEntity, nest: NestData) {}
    fun onUseNest(bird: LivingEntity, nest: NestData) {}
    fun onNestEntered(bird: LivingEntity, nest: NestData) {}
    fun onNestExited(bird: LivingEntity, nest: NestData) {}
    fun shouldReturnToNest(entity: LivingEntity, nestData: NestData): Boolean
    fun onTick(bird: LivingEntity, nest: NestData) {}
}