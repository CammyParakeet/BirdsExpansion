package com.glance.birds.nest.behavior

import com.glance.birds.nest.data.NestData
import org.bukkit.entity.Player

interface NestBehavior {
    fun onTick(nest: NestData) {}
    fun onPlayerInteract(nest: NestData, player: Player)
    fun onDecay(nest: NestData)
}