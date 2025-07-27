package com.glance.birds.nest.interaction

import com.glance.birds.nest.contents.NestContentsHandler
import com.glance.birds.nest.data.NestData
import com.glance.birds.util.item.isBundle
import org.bukkit.entity.Player

object BaseNestInteractionHandler {

    fun handleInteraction(nest: NestData, player: Player): Boolean {
        val item = player.inventory.itemInMainHand
        val loc = nest.pos.toLocation() ?: return false

        if (item.isBundle()) {
            // TODO fill bundle with eggs
            return true
        }

        if (!item.isEmpty) return false

        val extracted = NestContentsHandler.extractSingle(nest)
        if (extracted != null) {
            player.world.dropItemNaturally(loc, extracted)
            player.swingMainHand()
            return true
        }

        return false
    }

    // todo egg bundle here?

}