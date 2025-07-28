package com.glance.birds.nest.occupancy

import com.glance.birds.nest.data.NestData
import org.bukkit.entity.Mob
import java.util.UUID

object NestOccupancyManager {

    private val controllers = mutableMapOf<UUID, NestOccupancyController>()

    fun register(nest: NestData) {
        controllers.computeIfAbsent(nest.id) {
            NestOccupancyController(nest)
        }
    }

    fun unregister(nestId: UUID) {
        controllers.remove(nestId)
    }

    fun get(nestId: UUID): NestOccupancyController? = controllers[nestId]

    fun tickAll() {
        controllers.values.forEach { it.tick() }
    }

    fun assignToNest(mob: Mob, nest: NestData): OccupancyResult {
        return get(nest.id)?.assignMob(mob) ?: OccupancyResult.NotInitialized
    }

}