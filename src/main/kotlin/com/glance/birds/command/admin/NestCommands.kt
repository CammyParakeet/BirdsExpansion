package com.glance.birds.command.admin

import com.glance.birds.event.nest.NestEvent
import com.glance.birds.event.nest.block.NestPlaceEvent
import com.glance.birds.event.nest.contents.NestContentsAddedEvent
import com.glance.birds.event.nest.contents.NestContentsRemovedEvent
import com.glance.birds.event.nest.contents.NestEggAddedEvent
import com.glance.birds.event.nest.contents.NestEggRemovedEvent
import com.glance.birds.nest.Nest
import com.glance.birds.nest.data.NestData
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.nest.behavior.visual.NestVisualManager
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

        val nestData = NestData(
            variantId = variantId,
            pos = WorldBlockPos.fromLocation(loc),
            type = NestType.GROUND
        )
        val nest = Nest(nestData)

        val event = NestPlaceEvent(nest, NestPlaceEvent.Cause.COMMAND, sender)
        event.callEvent()
        if (event.isCancelled) return

        NestManager.placeNest(chunk, nest, 0L, debug)

        sender.sendMessage("Nest '$variantId' spawned at ${nestData.pos}")
    }

    @Command("birds nest check-chunk")
    fun checkChunk(sender: Player) {
        val chunk = sender.location.chunk

        val nests = NestManager.getNestsInChunk(chunk)

        sender.sendMessage("Nests in chunk: ${nests.map { "[Id: ${it.uniqueId} | Pos: ${it.data.pos.coords}]" }}")
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

        // TODO: clamp on nest data?
        val clamped = eggCount.coerceIn(0, 64)
        val previous = nest.state.eggCount

        if (clamped > previous) {
            val amountAdded = clamped - previous
            val event = NestEggAddedEvent(
                nest,
                cause = NestContentsAddedEvent.Cause.COMMAND,
                amount = amountAdded,
                whoAdded = sender
            )
            event.callEvent()
            if (event.isCancelled) return
        } else {
            val amountRemoved = previous - clamped
            val event = NestEggRemovedEvent(
                nest,
                reason = NestContentsRemovedEvent.Reason.COMMAND,
                amount = amountRemoved,
                whoUsed = sender
            )
            event.callEvent()
            if (event.isCancelled) return
        }

        nest.state.withEggs(clamped)

        runSync {
            NestVisualManager.updateVisuals(nest)
        }
    }

}