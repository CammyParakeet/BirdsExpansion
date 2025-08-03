package com.glance.birds.nest.item

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.variant.NestVariant
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.util.data.getPDC
import com.glance.birds.util.data.setPDC
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

object NestItemHandler {

    val NEST_ITEM_KEY = NamespacedKey(BirdsExpansion.instance(), "nest_item")

    fun ItemStack.isNestItem(): Boolean {
        return persistentDataContainer.has(NEST_ITEM_KEY)
    }

    fun getVariantId(item: ItemStack): String? {
        return item.getPDC<String>(NEST_ITEM_KEY)
    }

    fun getVariant(item: ItemStack): NestVariant? {
        return getVariantId(item)?.let { NestVariantRegistry.getById(it) }
    }

    fun markNestItem(item: ItemStack, variantId: String) {
        item.editMeta {
            it.setPDC(NEST_ITEM_KEY, variantId)
        }
    }

}