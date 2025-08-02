package com.glance.birds.nest.occupancy

import com.glance.birds.BirdsExpansion
import com.glance.birds.config.base.bird.NestAssignment
import com.glance.birds.event.nest.block.NestPlaceEvent
import com.glance.birds.nest.NestManager
import com.glance.birds.nest.behavior.mob.backoff.MobBackoffManager
import com.glance.birds.species.BirdSpeciesRegistry
import com.glance.birds.util.entity.hasAssignedNest
import com.glance.birds.util.sound.playSound
import com.glance.birds.util.time.backoff.BackoffResult
import com.glance.birds.util.world.particlesAsync
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Mob
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitTask
import kotlin.random.Random

// todo make configurable
private const val ASSIGNMENT_INTERVAL = 20 * 10L
private const val BIRD_RANDOM_TICK_CHANCE = 0.8 // todo lower

// TODO
object NestAssignmentEngine : Listener {

    private val plugin = BirdsExpansion.instance()
    private val backoff = MobBackoffManager()

    private var task: BukkitTask? = null

    fun isRunning(): Boolean = task?.isCancelled?.not() == true

    fun start() {
        if (isRunning()) {
            plugin.logger.warning("Nest Assignment Engine is already running")
            return
        }

        task = plugin.server.scheduler.runTaskTimer(
            plugin,
            Runnable {
                // todo configured worlds only?
                Bukkit.getWorlds().forEach { handleWorldTick(it) }
            },
            ASSIGNMENT_INTERVAL,
            ASSIGNMENT_INTERVAL
        )

        plugin.logger.info("Nest Assignment Engine has started")
    }

    fun stop() {
        task?.cancel()
        task = null
        plugin.logger.info("Nest Assignment Engine has stopped")
    }

    fun handleWorldTick(world: World) {
        world.livingEntities
            .asSequence()
            .filterIsInstance<Mob>()
            .filter { BirdSpeciesRegistry.isBird(it) }
            .filter { it.location.isChunkLoaded }
            .filter { Random.nextDouble() < BIRD_RANDOM_TICK_CHANCE }
            .forEach { tryAssignNest(it) }
    }

    // todo radius configure
    fun tryAssignNest(mob: Mob, searchRadius: Int = 8, chanceOverride: Double? = null): Boolean {
        if (mob.hasAssignedNest()) return false

        val uuid = mob.uniqueId
        val loc = mob.location
        val species = BirdSpeciesRegistry.get(mob) ?: return false

        // todo proper config system from species
        val particles = NestAssignment.Particles()
        val sounds = NestAssignment.Sounds()

        // check assignment chance override
        if (chanceOverride != null && Random.nextDouble() > chanceOverride) {
            loc.apply {
                particlesAsync { effect(particles.fail) }
                playSound(sounds.fail)
            }
            return false
        }

        if (chanceOverride == null && !backoff.canAttempt(uuid)) {
            return false
        }

        val nest = NestManager.getNearestNest(mob.location, searchRadius) ?: return false
        val result = nest.occupancyController?.assignMob(mob) ?: return false
        if (result == OccupancyResult.Assigned) {
            plugin.logger.info("Successfully assigned $mob to nest ${nest.data.pos.coords}")
            if (chanceOverride == null) backoff.reset(uuid)
            loc.particlesAsync {
                effect(particles.success)
            }
            loc.playSound(sounds.success)

            return true
        } else {
            val failResult = if (chanceOverride == null) backoff.registerAttempt(uuid) else BackoffResult.Cooldown
            loc.apply {
                when (failResult) {
                    is BackoffResult.Cooldown -> {
                        particlesAsync { effect(particles.cooldown) }
                        playSound(sounds.cooldown)
                    }
                    is BackoffResult.Backoff -> {
                        particlesAsync { effect(particles.fail) }
                        playSound(sounds.fail)
                    }
                }
            }
            return false
        }
    }

    @EventHandler
    fun onNestPlaced(event: NestPlaceEvent) {
        val world = event.nest.location?.world ?: return
        val chance = 0.6 //  todo from nest data or something, or maybe per species??
        val rad = 4
        val radSquared = rad * rad

        world.livingEntities
            .filterIsInstance<Mob>()
            .filter { it.location.distanceSquared(event.nest.location!!) <= radSquared }
            .filter { BirdSpeciesRegistry.isBird(it) }
            .forEach { mob ->
                if (Random.nextDouble() < chance) {
                    tryAssignNest(mob, rad * 2)
                }
            }
    }

}