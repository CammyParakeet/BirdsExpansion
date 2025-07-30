package com.glance.birds.event.nest.contents

import com.glance.birds.nest.Nest
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

class NestEggRemovedEvent(
    nest: Nest,
    amount: Int,
    reason: Reason,
    eggItem: ItemStack? = null,
    whoUsed: LivingEntity? = null
) : NestContentsRemovedEvent(nest, amount, reason, eggItem, whoUsed), Cancellable {

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic val handlerList = HandlerList()
    }

}