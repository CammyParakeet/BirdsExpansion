package com.glance.birds.util.world

import com.glance.birds.nest.NestManager
import org.bukkit.Material
import org.bukkit.block.Block

val leaves = setOf(
    Material.OAK_LEAVES, Material.DARK_OAK_LEAVES, Material.PALE_OAK_LEAVES,
    Material.BIRCH_LEAVES, Material.ACACIA_LEAVES, Material.AZALEA_LEAVES,
    Material.CHERRY_LEAVES, Material.JUNGLE_LEAVES, Material.MANGROVE_LEAVES,
    Material.SPRUCE_LEAVES, Material.FLOWERING_AZALEA_LEAVES
)

fun Block.isLeaves(): Boolean {
    return type.isLeaves()
}

fun Material.isLeaves(): Boolean {
    return this in leaves
}

fun Block.isNest(): Boolean {
    return NestManager.getNestAt(this.location) != null
}