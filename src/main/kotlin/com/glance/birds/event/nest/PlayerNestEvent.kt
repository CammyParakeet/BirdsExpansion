package com.glance.birds.event.nest

import com.glance.birds.nest.Nest
import org.bukkit.entity.Player

abstract class PlayerNestEvent(
    val player: Player,
    nest: Nest
) : NestEvent(nest)