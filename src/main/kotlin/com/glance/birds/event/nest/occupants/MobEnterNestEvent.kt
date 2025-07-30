package com.glance.birds.event.nest.occupants

import com.glance.birds.nest.Nest
import org.bukkit.entity.Mob
import org.bukkit.event.HandlerList

class MobEnterNestEvent(
    nest: Nest,
    mob: Mob
) : MobNestEvent(nest, mob) {

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic val handlerList = HandlerList()
    }

}