package com.glance.birds.nest.variant.draft

import com.glance.birds.config.base.DisplayConfig
import com.glance.birds.config.base.DisplayType
import com.glance.birds.config.base.TransformConfig
import com.glance.birds.nest.behavior.visual.handlers.base.BaseVisualConfig
import com.glance.birds.nest.behavior.visual.handlers.base.BaseVisualHandler
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.data.type.NestTypeData
import com.glance.birds.nest.item.NestItemHandler
import com.glance.birds.nest.spawn.NestSpawnConfig
import com.glance.birds.nest.variant.NestVariant
import com.glance.birds.nest.variant.recipe.ShapedNestRecipe
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector

val waterVisualConfig = run {
    val fan1 = DisplayConfig(
        type = DisplayType.BLOCK,
        material = Material.DEAD_HORN_CORAL_FAN,
        metadataKey = "water_visual_decor_1"
    )

    val fan2 = DisplayConfig(
        type = DisplayType.BLOCK,
        material = Material.TUBE_CORAL_FAN,
        transform = TransformConfig(
            translation = Vector(-.25F, 0F, .5F),
            rotBeforeScale = EulerAngle(0.0, Math.toRadians(45.0), 0.0)
        ),
        metadataKey = "water_visual_decor_2"
    )

    BaseVisualConfig(
        vanillaMaterial = Material.LILY_PAD,
        displayItems = listOf(fan1, fan2),
        supportsEggs = true,
        supportsFeathers = false
    )
}

val waterNestItem = run {
    val item = ItemStack(Material.LILY_PAD)
    item.editMeta {
        it.customName(Component.text("Water Nest").decoration(TextDecoration.ITALIC, false))
        it.persistentDataContainer.set(
            NestItemHandler.NEST_ITEM_KEY,
            PersistentDataType.STRING,
            "draft_water_nest"
        )
    }
    return@run item
}

val waterNestVariant = NestVariant(
    id = "draft_water_nest",
    supportedTypes = setOf(NestType.WATER),
    defaultTypeData = NestTypeData(
        visualHandler = BaseVisualHandler(waterVisualConfig),
        spawnConfig = NestSpawnConfig(chance = 0.1),
        baseBlockType = Material.LILY_PAD,
        dropItem = waterNestItem.clone()
    )
)

val waterRecipe = ShapedNestRecipe(
    variantId = "draft_water_nest",
    type = NestType.GROUND,
    shape = listOf("SLS", "LPL"),
    ingredients = mapOf(
        'S' to Material.STICK,
        'L' to Material.LEAF_LITTER,
        'P' to Material.LILY_PAD
    )
)