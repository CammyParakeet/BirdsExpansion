package com.glance.birds.nest.visual.handlers.base

import com.glance.birds.BirdsExpansion
import com.glance.birds.config.base.DisplayConfig
import com.glance.birds.config.base.DisplayType
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.visual.NestVisualHandler
import com.glance.birds.nest.data.NestState
import com.glance.birds.util.entity.editTransform
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.data.Waterlogged
import org.bukkit.entity.BlockDisplay
import org.bukkit.entity.Display
import org.bukkit.entity.ItemDisplay
import org.bukkit.persistence.PersistentDataType
import org.bukkit.util.Vector
import java.util.UUID

open class BaseVisualHandler(
    private val config: BaseVisualConfig
) : NestVisualHandler {

    companion object {
        private const val META_FEATHER_ENTITY = "visual_feather_entity"
        private const val META_EGG_ENTITY = "visual_egg_entity"
        private val NEST_DISPLAY_ENTITY = NamespacedKey(BirdsExpansion.instance(), "nest_display")
    }

    private val eggVisualizer = BaseEggVisualizer(config)

    override fun placeVisuals(location: Location, nestData: NestData, debug: Boolean) {
        placeBaseBlock(location)

        config.displayItems.forEach { cfg ->
            spawnDisplay(
                location,
                cfg,
                nestData
            )
        }
    }

    override fun restoreTransientVisuals(nestData: NestData, debug: Boolean) {
        nestData.pos.toLocation()?.let { loc ->
            config.displayItems.forEach { cfg ->
                spawnDisplay(
                    loc,
                    cfg,
                    nestData
                )
            }
        }

        updateVisualState(nestData, nestData.state)
    }

    private fun placeBaseBlock(location: Location) {
        val block = location.block

        config.vanillaMaterial?.let {
            val data = Bukkit.createBlockData(it)
            if (data is Waterlogged) {
                data.isWaterlogged = false
            }
            block.blockData = data
        }

        config.customBlockId?.let {
            // TODO custom through itemsadder/supplier
        }

        // TODO: update manager for placed state?
    }

    // TODO would be a model engine or something
    private fun spawnDisplay(
        location: Location,
        displayCfg: DisplayConfig,
        nestData: NestData,
        metadataKey: String? = null
    ): Display {
        val world = location.world

        val display: Display = when (displayCfg.type) {
            DisplayType.ITEM -> world.spawn(location, ItemDisplay::class.java) {
                it.setItemStack(displayCfg.getItem())
            }
            DisplayType.BLOCK -> run {
                val data = displayCfg.getBlockData() ?: error("Config could not create block data")

                world.spawn(location, BlockDisplay::class.java) {
                    it.block = data
                }
            }
            DisplayType.TEXT -> error("Text not yet supported")
        }

        display.apply {
            isPersistent = false
            viewRange = 16F
            persistentDataContainer.set(
                NEST_DISPLAY_ENTITY,
                PersistentDataType.STRING,
                nestData.id.toString()
            )

            editTransform {
                translation.set(-.25F, 0F, .5F)
                leftRotation.rotateY(Math.toRadians(45.0).toFloat())
            }
        }

        val key = metadataKey ?: displayCfg.metadataKey
        nestData.metadata += mapOf(
            key to display.uniqueId.toString()
        )

        return display
    }

    private fun removeEntity(
        nestData: NestData,
        metadataKey: String
    ) {
        val location = nestData.pos.toLocation() ?: return
        val world = location.world ?: return

        val uuidStr = nestData.metadata[metadataKey] as? String ?: return
        val uuid = UUID.fromString(uuidStr)

        world.getEntitiesByClasses(Display::class.java)
            .find { it.uniqueId == uuid }
            ?.remove()

        nestData.metadata -= metadataKey
    }

    private fun hasEntity(nestData: NestData, metadataKey: String): Boolean {
        val uuidStr = nestData.metadata[metadataKey] as? String ?: return false
        return try {
            UUID.fromString(uuidStr)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun cleanupVisuals(nestData: NestData, debug: Boolean) {
        val location = nestData.pos.toLocation() ?: return
        location.block.type = Material.AIR

        if (config.supportsEggs) {
            eggVisualizer.removeVisual(nestData, debug)
        }

        if (config.supportsFeathers) {
            // todo
        }

        if (config.supportsTrinkets) {
            // todo
        }

        config.displayItems.forEach {
            removeEntity(nestData, it.metadataKey)
        }
    }

    override fun updateVisualState(nestData: NestData, state: NestState, debug: Boolean) {
        val location = nestData.pos.toLocation() ?: return
        val world = location.world ?: return

        if (config.supportsFeathers) {
            if (state.hasFeathers) {
                // TODO: handle custom feathers
            } else {
                // TODO: remove feathers
            }
        }

        if (config.supportsEggs) {
            if (state.hasEggs) {
                eggVisualizer.updateVisual(nestData, state.eggCount)
            } else {
                eggVisualizer.removeVisual(nestData)
            }
        }

        if (config.supportsTrinkets) {
            val trinkets = nestData.getTrinkets()
            if (trinkets.isNotEmpty()) {
                // TODO: clamp list
            } else {
                // TODO: remove any
            }
        }

        // TODO damage or occupied
    }

}

data class BaseVisualConfig(
    val vanillaMaterial: Material? = null,
    val customBlockId: String? = null,
    val displayItems: List<DisplayConfig> = emptyList(),
    val supportsFeathers: Boolean = false,
    val supportsEggs: Boolean = false,
    val supportsTrinkets: Boolean = false,
    val featherOffset: Vector = Vector(),
    val eggOffset: Vector= Vector()
)