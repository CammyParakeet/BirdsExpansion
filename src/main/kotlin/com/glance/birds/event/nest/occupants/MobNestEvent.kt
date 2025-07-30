package com.glance.birds.event.nest.occupants

import com.glance.birds.event.nest.NestEvent
import com.glance.birds.nest.Nest
import org.bukkit.entity.Mob

abstract class MobNestEvent(
    nest: Nest,
    val mob: Mob
) : NestEvent(nest)