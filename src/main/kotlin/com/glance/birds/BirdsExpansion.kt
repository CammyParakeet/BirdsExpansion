package com.glance.birds

import com.glance.birds.command.admin.NestCommands
import com.glance.birds.command.engine.CommandHandler
import com.glance.birds.listener.NestChunkListener
import com.glance.birds.nest.NestManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class BirdsExpansion : JavaPlugin() {
    override fun onEnable() {
        logger.info("Birds Expansion initializing!")

        server.pluginManager.registerEvents(NestChunkListener(), this)

        CommandHandler.init(this)

        registerCommands()

        Bukkit.getWorlds().forEach { world ->
            world.loadedChunks.forEach { chunk ->
                NestManager.loadNestsForChunk(chunk)
            }
        }
    }

    private fun registerCommands() {
        CommandHandler.register(NestCommands())
    }

    override fun onDisable() {
        logger.info("Birds Expansion shutting down...")

        NestManager.shutdown()
    }

    companion object {
        fun instance(): BirdsExpansion {
            return getPlugin(BirdsExpansion::class.java)
        }
    }

}