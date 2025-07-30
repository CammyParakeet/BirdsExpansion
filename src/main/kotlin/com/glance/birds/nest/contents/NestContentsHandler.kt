package com.glance.birds.nest.contents

import com.glance.birds.event.nest.contents.NestContentsRemovedEvent
import com.glance.birds.event.nest.contents.NestEggRemovedEvent
import com.glance.birds.nest.Nest
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.behavior.visual.NestVisualManager
import com.glance.birds.util.item.isChickenEgg
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack

object NestContentsHandler {

    fun extractSingle(nest: Nest, target: LivingEntity? = null): ItemStack? {
        nest.location?: return null

        var extracted: ItemStack? = null

        val trinkets = nest.getTrinkets().toMutableList()
        if (trinkets.isNotEmpty()) {
            val removed = trinkets.removeFirst()
            nest.setTrinkets(trinkets)

            sync(nest)
            // TODO: get item from some trinket registry
            return null
        }

        if (nest.state.eggCount > 0) {
            nest.state.eggCount -= 1

            sync(nest)
            // TODO: get egg from variant id
            extracted = ItemStack(Material.BROWN_EGG)
        }

        if (nest.state.featherCount > 0) {
            nest.state.featherCount -= 1

            sync(nest)
            // TODO: feather from variant id
            extracted = ItemStack(Material.FEATHER)
        }

        if (extracted == null) return null

        val event: NestContentsRemovedEvent
        // todo proper egg item
        if (extracted.isChickenEgg()) {
            event = NestEggRemovedEvent(
                nest,
                extracted.amount,
                reason = NestContentsRemovedEvent.Reason.PLAYER,
                eggItem = extracted.clone(),
                whoUsed = target
            )
        }// else if (... feather?)
        else {
            event = NestContentsRemovedEvent(
                nest,
                extracted.amount,
                NestContentsRemovedEvent.Reason.PLAYER,
                extracted.clone(),
                target
            )
        }

        event.callEvent()
        if (event.isCancelled) {
            return null
        }

        return extracted
    }

    // todo get items from proper sources
    fun dropAll(nest: Nest) {
        val loc = nest.location?.toCenterLocation() ?: return
        val world = loc.world ?: return

        val eggs = nest.state.eggCount
        if (eggs > 0)  world.dropItemNaturally(loc, ItemStack(Material.BROWN_EGG, eggs))

        val feathers = nest.state.featherCount
        if (feathers > 0) world.dropItemNaturally(loc, ItemStack(Material.FEATHER, feathers))

        nest.getTrinkets().forEach { id ->
            //val item = TODO
        }

        nest.state.emptyContents()
        nest.setTrinkets(emptyList())

        sync(nest)
    }

    fun addContent(nest: NestData, item: ItemStack): Boolean {
        // TODO: Item should have custom pdc if is a specific thing like eggs or feathers
        // or a registered trinket - otherwise we should have regular item fallback for some?

        return false
    }

    fun sync(nest: Nest, debug: Boolean = false) {
        NestVisualManager.updateVisuals(nest, debug = debug)
    }

}