package com.glance.birds.command.dev

import com.glance.birds.nest.NestManager
import com.glance.birds.nest.occupancy.NestOccupancyManager
import com.glance.birds.species.BirdSpeciesRegistry
import com.glance.birds.util.task.runSync
import org.bukkit.entity.EntityType
import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Flag
import org.incendo.cloud.annotations.Permission

class DevCommands {

    @Command("birds dev assign-nearest-bird [range]")
    @Permission("birds.dev.assign")
    fun assignNearestBirdToNest(
        sender: Player,

        @Argument("range", description = "Search radius for nearby birds")
        range: Double?,

        @Flag("use-bird-pos", description = "Assign using the bird's position instead of the sender")
        useBirdPos: Boolean
    ) {
        val world = sender.world

        runSync {
            val bird = world.getNearbyLivingEntities(sender.location, range ?: 16.0)
                .filter { it.type == EntityType.PARROT || it.type == EntityType.CHICKEN }
                .minByOrNull { it.location.distanceSquared(sender.location) }

            if (bird == null) {
                sender.sendMessage("No nearby birds found within $range blocks")
                return@runSync
            }

            val mob = bird as? Mob ?: return@runSync
            val species = BirdSpeciesRegistry.get(mob) ?: return@runSync
            val origin = if (useBirdPos) mob.location else sender.location
            val nearestNest = NestManager.getNearestNest(origin, 64)

            if (nearestNest == null) {
                sender.sendMessage("No nest found near ${if (useBirdPos) "bird" else "player"}")
                return@runSync
            }

            val result = NestOccupancyManager.assignToNest(mob, nearestNest)
            sender.sendMessage("Attempted assignment to ${nearestNest.data.variantId} ${nearestNest.data.pos.coords} -> Result: ${result::class.simpleName}")
        }
    }

}