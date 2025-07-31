package com.glance.birds.util

import com.glance.birds.BirdsExpansion
import org.bukkit.NamespacedKey

fun namespacedKey(keyName: String): NamespacedKey {
    return NamespacedKey(BirdsExpansion.instance(), keyName)
}