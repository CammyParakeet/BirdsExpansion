package com.glance.birds.util.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun ItemStack.isChickenEgg(): Boolean {
    return this.type == Material.EGG ||
            this.type == Material.BROWN_EGG ||
            this.type == Material.BLUE_EGG
}