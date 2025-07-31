package com.glance.birds.nest.data.type

import com.glance.birds.nest.behavior.NestBehavior
import com.glance.birds.nest.spawn.NestSpawnConfig
import com.glance.birds.nest.behavior.visual.NestVisualHandler
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.inventory.ItemStack

data class NestTypeData(
    val visualHandler: NestVisualHandler,
    val spawnConfig: NestSpawnConfig,
    val baseBlockType: Material?,
    val dropItem: ItemStack,
    val behavior: NestBehavior? = null,
    val metadata: Map<String, Any> = emptyMap()
)
