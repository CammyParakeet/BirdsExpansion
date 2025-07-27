package com.glance.birds.util.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun ItemStack.isBundle(): Boolean {
    return type.name.contains("BUNDLE")
}