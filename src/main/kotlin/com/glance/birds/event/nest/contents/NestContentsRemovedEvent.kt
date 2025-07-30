package com.glance.birds.event.nest.contents

import com.glance.birds.event.nest.NestInteractionEvent
import com.glance.birds.nest.Nest
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

open class NestContentsRemovedEvent(
    nest: Nest,
    val amount: Int,
    val reason: Reason,
    val item: ItemStack?,
    whoUsed: LivingEntity?
) : NestInteractionEvent(nest, whoUsed), Cancellable {

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic val handlerList = HandlerList()
    }

    protected var cancelledFlag: Boolean = false

    override fun isCancelled(): Boolean = cancelledFlag
    override fun setCancelled(cancelled: Boolean) {
        this.cancelledFlag = cancelled
    }

    enum class Reason {
        PLAYER, USED, COMMAND, ENVIRONMENT
    }
}