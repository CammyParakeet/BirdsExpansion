package com.glance.birds.nest.variant.draft

import com.glance.birds.config.base.DisplayConfig
import com.glance.birds.config.base.DisplayType
import com.glance.birds.config.base.TransformConfig
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.data.type.NestTypeData
import com.glance.birds.nest.item.NestItemHandler
import com.glance.birds.nest.spawn.NestSpawnConfig
import com.glance.birds.nest.variant.NestVariant
import com.glance.birds.nest.behavior.visual.handlers.base.BaseVisualConfig
import com.glance.birds.nest.behavior.visual.handlers.base.BaseVisualHandler
import com.glance.birds.nest.variant.recipe.ShapedNestRecipe
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector

val draftMaterial = Material.DEAD_HORN_CORAL_FAN

val draftTreeVisualConfig = BaseVisualConfig(
    vanillaMaterial = draftMaterial,
    displayItems = listOf(
        DisplayConfig(
            type = DisplayType.BLOCK,
            material = Material.HORN_CORAL_FAN,
            transform = TransformConfig(
                translation = Vector(-.25F, 0F, .5F),
                rotBeforeScale = EulerAngle(0.0, Math.toRadians(45.0), 0.0)
            ),
            metadataKey = "draft__tree_visual_decor"
        )
    ),
    supportsEggs = true,
    supportsFeathers = true
)

val draftVisualConfig = BaseVisualConfig(
    vanillaMaterial = draftMaterial,
    displayItems = listOf(
        DisplayConfig(
            type = DisplayType.BLOCK,
            material = Material.DEAD_HORN_CORAL_FAN,
            transform = TransformConfig(
                translation = Vector(-.25F, 0F, .5F),
                rotBeforeScale = EulerAngle(0.0, Math.toRadians(45.0), 0.0)
            ),
            metadataKey = "draft_visual_decor"
        )
    ),
    supportsEggs = true,
    supportsFeathers = true
)

val draftNestItem = run {
    val item = ItemStack(Material.FIREFLY_BUSH)
    item.editMeta {
        it.customName(Component.text("Draft Nest").decoration(TextDecoration.ITALIC, false))
        it.persistentDataContainer.set(
            NestItemHandler.NEST_ITEM_KEY,
            PersistentDataType.STRING,
            "draft_basic_nest"
        )
    }
    return@run item
}

val draftTreeNestItem = run {
    val item = ItemStack(Material.DEAD_BUSH)
    item.editMeta {
        it.customName(Component.text("Draft Tree Nest").decoration(TextDecoration.ITALIC, false))
        it.persistentDataContainer.set(
            NestItemHandler.NEST_ITEM_KEY,
            PersistentDataType.STRING,
            "draft_tree_nest"
        )
    }
    return@run item
}

val draftNestVariant = NestVariant(
    id = "draft_basic_nest",
    supportedTypes = setOf(NestType.GROUND, NestType.WATER),
    defaultTypeData = NestTypeData(
        visualHandler = BaseVisualHandler(draftVisualConfig),
        spawnConfig = NestSpawnConfig(chance = 0.1),
        baseBlockType = draftMaterial,
        dropItem = draftNestItem.clone()
    )
)

val draftTreeNestVariant = NestVariant(
    id = "draft_tree_nest",
    supportedTypes = setOf(NestType.TREE),
    defaultTypeData = NestTypeData(
        visualHandler = BaseVisualHandler(draftTreeVisualConfig),
        spawnConfig = NestSpawnConfig(chance = 0.3),
        baseBlockType = draftMaterial,
        dropItem = draftTreeNestItem.clone()
    )
)

val draftRecipe = ShapedNestRecipe(
    variantId = "draft_basic_nest",
    type = NestType.GROUND,
    shape = listOf("S S", "LSL"),
    ingredients = mapOf(
        'S' to Material.STICK,
        'L' to Material.LEAF_LITTER
    )
)