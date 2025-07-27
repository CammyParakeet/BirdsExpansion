package com.glance.birds.nest.visual.handlers.base

import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.visual.NestFeatureVisualizer
import com.glance.birds.util.entity.editTransform
import org.bukkit.Material
import org.bukkit.block.data.type.TurtleEgg
import org.bukkit.entity.BlockDisplay

class BaseEggVisualizer(
    private val config: BaseVisualConfig,
    private val maxVisuals: Int = 4
) : NestFeatureVisualizer("egg_display_") {

    override fun updateVisual(data: NestData, debug: Boolean) {
        updateVisual(data, 1, debug)
    }

    override fun updateVisual(data: NestData, count: Int, debug: Boolean) {
        if (!config.supportsEggs) return
        removeVisual(data, debug)

        val location = data.pos.toLocation() ?: return
        val world = location.world ?: return

        val clampedCount = (count.coerceIn(0, maxVisuals)).coerceIn(0, 4)
        if (clampedCount < 1) return

        val display = world.spawn(location, BlockDisplay::class.java) { d ->
            val eggBlock = Material.TURTLE_EGG.createBlockData()
            eggBlock.apply {
                (this as? TurtleEgg)?.let { egg ->
                    egg.eggs = clampedCount
                }
            }

            d.block = eggBlock
            d.isPersistent = false
            d.editTransform {
                translation.set(config.eggOffset.toVector3f())
                scale.set(0.75F)
            }
        }

        registerEntity(data, display)
    }

}