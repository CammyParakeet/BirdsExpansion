package com.glance.birds.command.admin

import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.nest.visual.NestVisualManager
import com.glance.birds.util.task.runSync
import com.glance.birds.util.world.WorldBlockPos
import org.bukkit.Location
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Flag
import org.incendo.cloud.annotations.Permission
import org.incendo.cloud.annotations.suggestion.Suggestions

class NestCommands {

    @Suggestions("nest-variant-ids")
    fun suggestNestVariants(input: String): List<String> {
        return NestVariantRegistry.getAll().map { it.id }
    }

    @Command("birds nest spawn <variantId> [location]")
    @Permission("birds.spawnnest")
    fun spawnNest(
        sender: Player,
        @Argument("variantId", suggestions = "nest-variant-ids")
        variantId: String,

        @Flag("debug")
        debug: Boolean,

        @Argument("location")
        location: Location? = null,
    ) {
        val variant = NestVariantRegistry.getById(variantId)
        if (variant == null) {
            sender.sendMessage("Variant ID '$variantId' not found")
        }

        val loc = location ?: sender.location
        val chunk = loc.chunk

        val nest = NestData(
            variantId = variantId,
            pos = WorldBlockPos.fromLocation(loc),
            type = NestType.GROUND
        )

        NestManager.placeNest(chunk, nest, debug)
        sender.sendMessage("Nest '$variantId' spawned at ${nest.pos}")
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

    @Command("birds nest set-nearest-eggs <amount>")
    @Permission("birds.admin.seteggs")
    fun setNearestEggCount(
        sender: Player,
        @Argument("amount") eggCount: Int,
        @Flag("debug") debug: Boolean = false
    ) {
        val nest = NestManager.getNearestNest(sender.location, 6)

        if (nest == null) {
            sender.sendMessage("No nests nearby")
            return
        }

        // TODO: clamp on nest data
        val clamped = eggCount.coerceIn(0, 64)

        nest.visualState.apply {
            this.eggCount = clamped
            this.hasEggs = clamped > 0
        }

        runSync {
            NestVisualManager.updateVisuals(nest)
        }
    }

}