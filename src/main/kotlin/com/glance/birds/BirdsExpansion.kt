package com.glance.birds

import com.glance.birds.command.admin.NestCommands
import com.glance.birds.command.dev.DevCommands
import com.glance.birds.command.engine.CommandHandler
import com.glance.birds.listener.bird.PlayerSeedBirdListener
import com.glance.birds.listener.nest.NestChunkListener
import com.glance.birds.listener.nest.NestInteractionListener
import com.glance.birds.listener.nest.NestPhysicsManager
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.behavior.NestTicker
import com.glance.birds.nest.occupancy.NestAssignmentEngine
import com.glance.birds.nest.spawn.SpawnerTask
import com.glance.birds.nest.spawn.patch.NestPatcher
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.nest.variant.draft.draftNestVariant
import com.glance.birds.nest.variant.draft.draftRecipe
import com.glance.birds.nest.variant.draft.draftTreeNestVariant
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class BirdsExpansion : JavaPlugin() {
    override fun onEnable() {
        logger.info("Birds Expansion initializing!")

        server.pluginManager.apply {
            registerEvents(NestChunkListener, this@BirdsExpansion)
            registerEvents(NestPhysicsManager, this@BirdsExpansion)
            registerEvents(NestInteractionListener, this@BirdsExpansion)
            registerEvents(NestAssignmentEngine, this@BirdsExpansion)
            registerEvents(PlayerSeedBirdListener, this@BirdsExpansion)
        }

        CommandHandler.init(this)

        registerCommands()

        NestVariantRegistry.register(draftNestVariant)
        NestVariantRegistry.register(draftTreeNestVariant)

        // todo config loading of recipes
        Bukkit.addRecipe(draftRecipe.toBukkitRecipe())

        Bukkit.getWorlds().forEach { world ->
            world.loadedChunks.forEach { chunk ->
                NestPatcher.patchChunk(chunk)
                NestManager.loadNestsForChunk(chunk, true)
            }
        }

        SpawnerTask.start()
        NestTicker.start()
        NestAssignmentEngine.start()
    }

    private fun registerCommands() {
        CommandHandler.register(NestCommands())
        CommandHandler.register(DevCommands())
    }

    override fun onDisable() {
        logger.info("Birds Expansion shutting down...")

        SpawnerTask.stop()
        NestManager.shutdown()
        NestVariantRegistry.clear()
    }

    companion object {
        fun instance(): BirdsExpansion {
            return getPlugin(BirdsExpansion::class.java)
        }
    }

}