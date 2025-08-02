package com.glance.birds.nest.occupancy

import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import java.util.UUID

object BirdPairingManager {

    private val pairedBirds = mutableMapOf<UUID, UUID>() // player -> bird

    fun pair(player: Player, bird: Mob) {
        pairedBirds[player.uniqueId] = bird.uniqueId
    }

    fun getPairedBird(player: Player): Mob? {
        val id = pairedBirds[player.uniqueId] ?: return null
        return player.world.getEntity(id) as? Mob
    }

    fun clear(player: Player) {
        pairedBirds.remove(player.uniqueId)
    }

    fun isPaired(player: Player): Boolean = pairedBirds.containsKey(player.uniqueId)

}