package com.glance.birds.listener.bird

import com.glance.birds.nest.NestManager.getIfNest
import com.glance.birds.nest.occupancy.BirdPairingManager
import com.glance.birds.nest.occupancy.NestAssignmentEngine
import com.glance.birds.species.BirdSpeciesRegistry
import com.glance.birds.util.sound.playSound
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.entity.Mob
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

object PlayerSeedBirdListener : Listener {

    private const val PAIR_RADIUS = 10

    @EventHandler
    fun onSeedUsedOnBird(event: PlayerInteractEntityEvent) {
        val player = event.player
        // TODO: as whatever our custom bird interaction would be handled too
        val target = event.rightClicked as? Mob ?: return
        val item = player.inventory.itemInMainHand

        // TODO: filter specific seeds?? or nah
        if (!item.type.name.contains("SEEDS")) return
        val species = BirdSpeciesRegistry.get(target) ?: return

        BirdPairingManager.pair(player, target)
        species.preferredNestBehavior?.setPairMode(target)
    }

    @EventHandler
    fun onSeedUsedOnNest(event: PlayerInteractEvent) {
        // todo might not just be block for a nest?
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        val nest = event.clickedBlock?.getIfNest() ?: return

        val player = event.player
        val item = event.item ?: return

        if (!item.type.name.contains("SEEDS")) return

        val birdMob = BirdPairingManager.getPairedBird(player) ?: return
        if (birdMob.isDead || birdMob.location.world != player.location.world) return

        val species = BirdSpeciesRegistry.get(birdMob) ?: return
        val behavior = species.preferredNestBehavior ?: return
        if (!behavior.isInPairMode(birdMob)) return

        // todo configurable
        val rangeSq = PAIR_RADIUS * PAIR_RADIUS
        if (nest.location!!.distanceSquared(birdMob.location) > rangeSq) return

        val success = NestAssignmentEngine.tryAssignNest(birdMob, PAIR_RADIUS, 1.0)
        if (success) {
            birdMob.location.playSound(Sound.ENTITY_CHICKEN_EGG)
            player.swingMainHand()

            if (player.gameMode != GameMode.CREATIVE) {
                item.amount -= 1
            }

            BirdPairingManager.clear(player)
            behavior.clearPairMode(birdMob)
            event.isCancelled = true
        }
    }

}