package com.glance.birds.nest.interaction

import com.glance.birds.event.nest.NestEvent
import com.glance.birds.event.nest.contents.NestContentsAddedEvent
import com.glance.birds.event.nest.contents.NestContentsRemovedEvent
import com.glance.birds.event.nest.contents.NestEggAddedEvent
import com.glance.birds.event.nest.contents.NestEggRemovedEvent
import com.glance.birds.nest.Nest
import com.glance.birds.nest.behavior.sound.playAddEggSound
import com.glance.birds.nest.behavior.sound.playExtractEggSound
import com.glance.birds.nest.contents.NestContentsHandler
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.behavior.visual.NestVisualManager
import com.glance.birds.util.item.isBundle
import com.glance.birds.util.item.isChickenEgg
import com.glance.birds.util.item.tryAddItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BundleMeta

object BaseNestInteractionHandler {

    fun handleInteraction(nest: Nest, player: Player): Boolean {
        val item = player.inventory.itemInMainHand
        val loc = nest.location ?: return false

        if (item.isBundle()) {
            val success = tryFillBundleWithEggs(nest, item, player)
            if (success) {
                nest.playExtractEggSound()
            }
            return success
        }

        // TODO: items that can be placed in, quick draft example
        if (item.isChickenEgg()) {
            val eggItem = item.clone().apply { amount = 1 }
            val event = NestEggAddedEvent(
                nest = nest,
                amount = 1,
                eggItem = eggItem,
                cause = NestContentsAddedEvent.Cause.PLAYER,
                whoAdded = player
            )
            event.callEvent()
            if (event.isCancelled) return false

            val maxEggs = 16 // todo from nest data somehow
            nest.state.eggCount += 1
            item.amount -= 1
            // todo max visual count
            if (nest.state.eggCount <= 4) {
                nest.update()
            }
            nest.playAddEggSound()
            return true
        }

        if (!item.isEmpty) return false

        val extracted = NestContentsHandler.extractSingle(nest, player)
        if (extracted != null) {
            nest.playExtractEggSound() // todo based on extracted
            player.world.dropItemNaturally(loc, extracted)
            player.swingMainHand()
            return true
        }

        return false
    }

    fun tryFillBundleWithEggs(nest: Nest, bundle: ItemStack, player: Player): Boolean {
        val currentEggs = nest.state.eggCount
        if (currentEggs <= 0) return false
        val eggsToAdd = currentEggs.coerceAtMost(64)

        // TODO: correct egg item from nest
        val item = ItemStack(Material.BROWN_EGG)
        item.amount = eggsToAdd

        val event = NestEggRemovedEvent(
            nest,
            eggsToAdd,
            NestContentsRemovedEvent.Reason.PLAYER,
            eggItem = item.clone(),
            whoUsed = player
        )
        event.callEvent()
        if (event.isCancelled) return false

        bundle.editMeta {
            val added = (it as? BundleMeta)?.tryAddItem(item) ?: 0
            item.amount -= added

            nest.state.eggCount -= added

            nest.update()
        }

        return true
    }

}