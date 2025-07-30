package com.glance.birds.event.nest

import com.glance.birds.nest.Nest
import org.bukkit.entity.LivingEntity

abstract class NestInteractionEvent(
    nest: Nest,
    val user: LivingEntity?
) : NestEvent(nest)