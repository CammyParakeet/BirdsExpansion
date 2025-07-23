package com.glance.birds

import com.glance.birds.command.admin.NestCommands
import com.glance.birds.command.engine.CommandHandler
import com.glance.birds.listener.NestChunkListener
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.spawn.SpawnerTask
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.nest.variant.draft.draftNestVariant
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class BirdsExpansion : JavaPlugin() {
    override fun onEnable() {
        logger.info("Birds Expansion initializing!")

        server.pluginManager.registerEvents(NestChunkListener(), this)

        CommandHandler.init(this)

        registerCommands()

        NestVariantRegistry.register(draftNestVariant)

        Bukkit.getWorlds().forEach { world ->
            world.loadedChunks.forEach { chunk ->
                NestManager.loadNestsForChunk(chunk)
            }
        }

        SpawnerTask.start()
    }

    private fun registerCommands() {
        CommandHandler.register(NestCommands())
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