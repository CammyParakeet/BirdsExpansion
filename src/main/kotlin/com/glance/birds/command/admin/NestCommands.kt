package com.glance.birds.command.admin

import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.util.world.WorldBlockPos
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission

class NestCommands {

    @Command("birds nest spawn")
    @Permission("birds.spawnnest")
    fun spawnNest(sender: Player) {
        val loc = sender.location
        val chunk = loc.chunk

        val nest = NestData(
            pos = WorldBlockPos.fromLocation(loc),
            type = NestType.GROUND
        )

        NestManager.addNest(chunk, nest)
        sender.sendMessage("Nest spawned at ${nest.pos}")
    }

    @Command("birds nest check-chunk")
    fun checkChunk(sender: Player) {
        val chunk = sender.location.chunk

        val nests = NestManager.getNestsInChunk(chunk)

        sender.sendMessage("Nests in chunk: $nests")
    }

    @Command("birds nest flush")
    fun flushNests(sender: Player) {
        val chunk = sender.location.chunk
        NestManager.flushChunk(chunk)

        sender.sendMessage("Flushed chunk $chunk - now has nest data ${NestManager.getNestsInChunk(chunk)}")
    }

}