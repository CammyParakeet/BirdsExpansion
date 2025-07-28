package com.glance.birds.util.item

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BundleMeta

const val MAX_WEIGHT = 64

fun ItemStack.isBundle(): Boolean {
    return type.name.contains("BUNDLE")
}

fun BundleMeta.getCurrentWeight(): Int {
    return items.sumOf { it.getBundleWeight() }
}

fun ItemStack.getBundleWeight(bundleWeight: Int = MAX_WEIGHT): Int {
    // todo override max weight from a designated PDC?

    return bundleWeight / maxStackSize
}

fun BundleMeta.canFit(item: ItemStack): Boolean {
    val available = MAX_WEIGHT - getCurrentWeight()
    return item.getBundleWeight() * item.amount <= available
}

fun BundleMeta.tryAddItem(item: ItemStack): Int {
    val maxWeight = MAX_WEIGHT
    val unitWeight = item.getBundleWeight(maxWeight)

    val availableWeight = maxWeight - this.getCurrentWeight()
    if (availableWeight <= 0 || unitWeight > availableWeight) return 0

    val maxAddable = availableWeight / unitWeight
    val toAdd = minOf(item.amount, maxAddable)

    repeat(toAdd) {
        this.addItem(item.clone().apply { amount = 1 })
    }

    return toAdd
}