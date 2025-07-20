package com.glance.birds.command.engine

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

object CommandHandler {

    private lateinit var commandManager: CommandManager

    fun init(plugin: JavaPlugin) {
        commandManager = CommandManager(plugin)
    }

    fun register(command: Any) {
        try {
            commandManager.registerAnnotated(command)
        } catch (e: Exception) {
            commandManager
                .owningPlugin()
                .logger
                .log(Level.SEVERE, "Failed to register command: ${command::class.simpleName}", e)
        }
    }

}