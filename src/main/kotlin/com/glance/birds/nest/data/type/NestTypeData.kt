package com.glance.birds.nest.data.type

import com.glance.birds.nest.behavior.NestBehavior
import com.glance.birds.nest.spawn.NestSpawnConfig
import com.glance.birds.nest.visual.NestVisualHandler
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class NestTypeData(
    val baseBlock: Material,
    val visualHandler: NestVisualHandler,
    val spawnConfig: NestSpawnConfig,
    val dropItem: ItemStack,
    val customBlockId: String? = null,
    val behavior: NestBehavior? = null,
    val metadata: Map<String, Any> = emptyMap()
)
