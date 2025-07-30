package com.glance.birds.event.nest.contents

import com.glance.birds.nest.Nest
import org.bukkit.entity.LivingEntity
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

class NestEggAddedEvent(
    nest: Nest,
    amount: Int,
    cause: Cause,
    eggItem: ItemStack? = null,
    whoAdded: LivingEntity? = null
) : NestContentsAddedEvent(nest, amount, cause, eggItem, whoAdded) {

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic val handlerList = HandlerList()
    }

}