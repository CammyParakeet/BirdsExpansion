package com.glance.birds.nest.visual.handlers

import com.glance.birds.BirdsExpansion
import com.glance.birds.config.base.DisplayItemConfig
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.visual.NestVisualHandler
import com.glance.birds.nest.visual.NestVisualState
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
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

    override fun placeVisuals(location: Location, nestData: NestData) {
        placeBaseBlock(location)

        config.displayItems.forEach { cfg ->
            spawnDisplay(
                location,
                cfg,
                nestData
            )
        }
    }

    private fun placeBaseBlock(location: Location) {
        val block = location.block

        config.vanillaMaterial?.let {
            block.type = it
        }

        config.customBlockId?.let {
            // TODO custom through itemsadder/supplier
        }

        // TODO: update manager for placed state?
    }

    // TODO would be a model engine or something
    private fun spawnDisplay(
        location: Location,
        displayCfg: DisplayItemConfig,
        nestData: NestData,
        metadataKey: String? = null
    ): Display {
        val display = location.world.spawn(location, ItemDisplay::class.java) {
            it.setItemStack(displayCfg.getItem())
            it.isPersistent = false
            it.viewRange = 16F
            it.persistentDataContainer.set(
                NEST_DISPLAY_ENTITY,
                PersistentDataType.STRING,
                nestData.id.toString()
            )

            // todo edit transform or when its in a model engine
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

    override fun cleanupVisuals(nestData: NestData) {
        val location = nestData.pos.toLocation() ?: return
        location.block.type = Material.AIR

        removeEntity(nestData, META_EGG_ENTITY)
        removeEntity(nestData, META_FEATHER_ENTITY)

        config.displayItems.forEach {
            removeEntity(nestData, it.metadataKey)
        }
    }

    override fun updateVisualState(nestData: NestData, state: NestVisualState) {
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
                // TODO: handle egg visual
            } else {
                // TODO: remove egg visual
            }
        }

        // TODO damage or occupied
    }

}

data class BaseVisualConfig(
    val vanillaMaterial: Material? = null,
    val customBlockId: String? = null,
    val displayItems: List<DisplayItemConfig> = emptyList(),
    val supportsFeathers: Boolean = false,
    val supportsEggs: Boolean = false,
    val featherOffset: Vector = Vector(),
    val eggOffset: Vector= Vector()
)