package com.glance.birds.event.nest.occupants

import com.glance.birds.nest.Nest
import org.bukkit.entity.Mob
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList

class MobAssignedToNestEvent(
    nest: Nest,
    mob: Mob
) : MobNestEvent(nest, mob), Cancellable {

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic val handlerList = HandlerList()
    }

    private var cancelledFlag: Boolean = false

    override fun isCancelled(): Boolean = cancelledFlag
    override fun setCancelled(cancelled: Boolean) {
        this.cancelledFlag = cancelled
    }

}