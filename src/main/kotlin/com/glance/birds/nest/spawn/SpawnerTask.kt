package com.glance.birds.nest.spawn

import com.glance.birds.BirdsExpansion
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import kotlin.random.Random

// todo should be cfg
const val SPAWNER_INTERVAL = 20 * 30

object SpawnerTask {

    private val plugin = BirdsExpansion.instance()
    private const val CHUNK_RADIUS = 9 // chunks around player to tick - todo server sim + cfg

    // todo also from actual cfg
    private val config = NestSpawnConfig(
        chance = 0.01,
        maxNearbyNests = 3
    )

    private var task: BukkitTask? = null

    fun isRunning(): Boolean = task != null && !task!!.isCancelled

    fun start() {
        if (task != null) {
            plugin.logger.warning("BirdsExpansion Nests SpawnerTask is already running")
            return
        }

        task = plugin.server.scheduler.runTaskTimer(
            plugin,
            Runnable { run() },
            SPAWNER_INTERVAL.toLong(),
            SPAWNER_INTERVAL.toLong()
        )
    }

    fun run() {
        Bukkit.getOnlinePlayers().forEach { player ->
            val world = player.world
            // todo only configured worlds

            val playerChunkX = player.location.chunk.x
            val playerChunkZ = player.location.chunk.z

            val dx = Random.nextInt(-CHUNK_RADIUS, CHUNK_RADIUS + 1)
            val dz = Random.nextInt(-CHUNK_RADIUS, CHUNK_RADIUS + 1)
            val chunk = world.getChunkAt(playerChunkX + dx, playerChunkZ + dz)

            if (!chunk.isLoaded) return@forEach

            val result = NestSpawner.attemptSpawnNest(chunk, config)
            if (result is SpawnResult.Spawned) {
                plugin.logger.info("BirdsExpansion Nests SpawnerTask: Spawned new nest near ${player.name} in chunk $chunk at ${result.nest.pos.coords}")
            }
        }
    }

    fun stop() {
        task?.cancel()
        task = null
        plugin.logger.info("BirdsExpansion Nests SpawnerTask stopped")
    }

}