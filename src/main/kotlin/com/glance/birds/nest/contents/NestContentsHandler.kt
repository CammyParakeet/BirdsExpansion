package com.glance.birds.nest.contents

import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.visual.NestVisualManager
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object NestContentsHandler {

    fun extractSingle(nest: NestData): ItemStack? {
        nest.pos.toLocation() ?: return null

        val trinkets = nest.getTrinkets().toMutableList()
        if (trinkets.isNotEmpty()) {
            val removed = trinkets.removeFirst()
            nest.setTrinkets(trinkets)

            sync(nest)
            // TODO: get item from some trinket registry
        }

        if (nest.visualState.eggCount > 0) {
            nest.visualState.eggCount -= 1

            sync(nest)
            // TODO: get egg from variant id
            return ItemStack(Material.BROWN_EGG)
        }

        if (nest.visualState.featherCount > 0) {
            nest.visualState.featherCount -= 1

            sync(nest)
            // TODO: feather from variant id
            return ItemStack(Material.FEATHER)
        }

        return null
    }

    // todo get items from proper sources
    fun dropAll(nest: NestData) {
        val loc = nest.pos.toLocation()?.toCenterLocation() ?: return
        val world = loc.world ?: return

        val eggs = nest.visualState.eggCount
        if (eggs > 0)  world.dropItemNaturally(loc, ItemStack(Material.BROWN_EGG, eggs))

        val feathers = nest.visualState.featherCount
        if (feathers > 0) world.dropItemNaturally(loc, ItemStack(Material.FEATHER, feathers))

        nest.getTrinkets().forEach { id ->
            //val item = TODO
        }

        nest.visualState.emptyContents()
        nest.setTrinkets(emptyList())

        sync(nest)
    }

    fun addContent(nest: NestData, item: ItemStack): Boolean {
        // TODO: Item should have custom pdc if is a specific thing like eggs or feathers
        // or a registered trinket - otherwise we should have regular item fallback for some?

        return false
    }

    fun sync(nest: NestData, debug: Boolean = false) {
        NestVisualManager.updateVisuals(nest, debug = debug)
    }

}