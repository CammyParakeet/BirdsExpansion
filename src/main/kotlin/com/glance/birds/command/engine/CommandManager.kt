package com.glance.birds.command.engine

import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.SenderMapper
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.meta.SimpleCommandMeta
import org.incendo.cloud.paper.LegacyPaperCommandManager

class CommandManager(plugin: JavaPlugin) : LegacyPaperCommandManager<CommandSender>(
    plugin,
    ExecutionCoordinator.asyncCoordinator(),
    SenderMapper.identity()
) {

    private val annotationParser = AnnotationParser(
        this,
        CommandSender::class.java
    ) { _ -> SimpleCommandMeta.empty() }

    fun registerAnnotated(command: Any) {
        this.annotationParser.parse(command)
    }

}