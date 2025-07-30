package com.glance.birds.event.nest

import com.glance.birds.nest.Nest
import org.bukkit.event.Event

abstract class NestEvent(
    val nest: Nest,
    isAsync: Boolean = false
) : Event(isAsync)