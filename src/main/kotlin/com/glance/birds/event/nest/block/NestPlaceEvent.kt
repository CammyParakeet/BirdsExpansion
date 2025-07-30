package com.glance.birds.event.nest.block

import com.glance.birds.event.nest.NestEvent
import com.glance.birds.nest.Nest
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList

class NestPlaceEvent(
    nest: Nest,
    val cause: Cause,
    val whoPlaced: LivingEntity? = null
) : NestEvent(nest), Cancellable {

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic val handlerList = HandlerList()
    }

    private var cancelledFlag: Boolean = false

    override fun isCancelled(): Boolean = cancelledFlag
    override fun setCancelled(cancelled: Boolean) {
        this.cancelledFlag = cancelled
    }

    enum class Cause {
        PLAYER, COMMAND, ENVIRONMENT
    }

}