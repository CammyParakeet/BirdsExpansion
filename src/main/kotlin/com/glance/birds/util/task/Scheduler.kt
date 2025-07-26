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

fun runAsync(delay: Long, action: () -> Unit) {
    val plugin = BirdsExpansion.instance()
    plugin.server.scheduler.runTaskLaterAsynchronously(plugin, action, delay)
}

fun runSync(delay :Long, action: () -> Unit) {
    val plugin = BirdsExpansion.instance()
    plugin.server.scheduler.runTaskLater(plugin, action, delay)
}