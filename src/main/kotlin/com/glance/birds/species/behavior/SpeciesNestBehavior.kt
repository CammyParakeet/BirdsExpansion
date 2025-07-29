package com.glance.birds.species.behavior

import com.glance.birds.nest.Nest
import org.bukkit.entity.LivingEntity

interface SpeciesNestBehavior {
    fun onTick(bird: LivingEntity, nest: Nest, inNest: Boolean = false) {}

    fun onApproachNest(bird: LivingEntity, nest: Nest) {}
    fun onUseNest(bird: LivingEntity, nest: Nest) {}
    fun onNestEntered(bird: LivingEntity, nest: Nest) {}
    fun onNestExited(bird: LivingEntity, nest: Nest) {}

    fun shouldExitNest(entity: LivingEntity, nest: Nest): Boolean
    fun shouldReturnToNest(entity: LivingEntity, nest: Nest): Boolean

}