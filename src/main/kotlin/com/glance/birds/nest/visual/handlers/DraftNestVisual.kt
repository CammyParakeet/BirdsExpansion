package com.glance.birds.nest.visual.handlers

import com.glance.birds.config.base.DisplayItemConfig
import org.bukkit.Material
import org.bukkit.util.EulerAngle

val draftVisualConfig = BaseVisualConfig(
    vanillaMaterial = Material.DEAD_TUBE_CORAL_FAN,
    displayItems = listOf(
        DisplayItemConfig(
            material = Material.DEAD_TUBE_CORAL_FAN,
            rotation = EulerAngle(0.0, Math.toRadians(45.0), 0.0),
            metadataKey = "draft_visual_decor"
        )
    ),
    supportsEggs = true,
    supportsFeathers = true
)