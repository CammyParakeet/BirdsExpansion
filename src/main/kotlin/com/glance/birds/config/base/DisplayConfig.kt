package com.glance.birds.config.base

import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.inventory.ItemStack

data class DisplayConfig(
    val type: DisplayType = DisplayType.ITEM,
    val material: Material? = null,
    val customId: String? = null,
    val transform: TransformConfig? = null,
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

    fun getBlockData(): BlockData? {
        return material?.createBlockData()
    }

}

enum class DisplayType {
    ITEM, BLOCK, TEXT
}