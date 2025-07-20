package com.glance.birds.util.task

import com.glance.birds.BirdsExpansion

fun runAsync(action: () -> Unit) {
    val plugin = BirdsExpansion.instance()
    plugin.server.scheduler.runTaskAsynchronously(plugin, action)
}

fun runSync(action: () -> Unit) {
    val plugin = BirdsExpansion.instance()
    plugin.server.scheduler.runTask(plugin, action)
}