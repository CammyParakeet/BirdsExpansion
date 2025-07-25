package com.glance.birds.nest.variant.draft

import com.glance.birds.config.base.DisplayConfig
import com.glance.birds.config.base.DisplayType
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.data.type.NestTypeData
import com.glance.birds.nest.item.NestItemHandler
import com.glance.birds.nest.spawn.NestSpawnConfig
import com.glance.birds.nest.variant.NestVariant
import com.glance.birds.nest.visual.handlers.BaseVisualConfig
import com.glance.birds.nest.visual.handlers.BaseVisualHandler
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.EulerAngle

val draftVisualConfig = BaseVisualConfig(
    vanillaMaterial = Material.DEAD_TUBE_CORAL_FAN,
    displayItems = listOf(
        DisplayConfig(
            type = DisplayType.BLOCK,
            material = Material.DEAD_TUBE_CORAL_FAN,
            rotation = EulerAngle(0.0, Math.toRadians(45.0), 0.0),
            metadataKey = "draft_visual_decor"
        )
    ),
    supportsEggs = true,
    supportsFeathers = true
)

val draftNestItem = run {
    val item = ItemStack(Material.DEAD_BUSH)
    item.editMeta {
        it.persistentDataContainer.set(
            NestItemHandler.NEST_ITEM_KEY,
            PersistentDataType.STRING,
            "draft_basic_nest"
        )
    }
    return@run item
}

val draftNestVariant = NestVariant(
    id = "draft_basic_nest",
    supportedTypes = setOf(NestType.TREE, NestType.GROUND, NestType.WATER),
    defaultTypeData = NestTypeData(
        visualHandler = BaseVisualHandler(draftVisualConfig),
        spawnConfig = NestSpawnConfig(chance = 0.2),
        dropItem = draftNestItem.clone()
    )
)