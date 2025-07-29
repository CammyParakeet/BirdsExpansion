package com.glance.birds.nest.behavior

import com.glance.birds.BirdsExpansion
import com.glance.birds.nest.NestManager
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

object NestTicker {

    private const val SYNC_INTERVAL = 20L
    private const val ASYNC_INTERVAL = 20L

    fun start(plugin: Plugin = BirdsExpansion.instance()) {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            tickSync()
        }, 0L, SYNC_INTERVAL)

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            tickAsync()
        }, 0L, ASYNC_INTERVAL)
    }

    private fun tickSync() {
        NestManager.getLoadedNests().forEach { it.tick() }
    }

    private fun tickAsync() {
        NestManager.getLoadedNests().forEach { it.tickAsync() }
    }

}