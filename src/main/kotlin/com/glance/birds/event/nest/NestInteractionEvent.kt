package com.glance.birds.event.nest

import com.glance.birds.nest.Nest
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.block.Action

open class NestInteractionEvent(
    nest: Nest,
    val user: LivingEntity? = null,
    val action: Action? = null,
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

}