package com.glance.birds.config.base

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector

data class DisplayItemConfig(
    val material: Material? = null,
    val customId: String? = null,
    val offset: Vector = Vector(),
    val rotation: EulerAngle = EulerAngle(0.0, 0.0, 0.0),
    val scale: Vector = Vector(),
    val metadataKey: String
) {
    // todo proper itemstack config - non data class?
    fun getItem(): ItemStack {
        var item: ItemStack = ItemStack.empty()
        material?.let { item = ItemStack(it) }
        // todo
        customId?.let { }

        return item
    }
}