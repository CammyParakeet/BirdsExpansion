package com.glance.birds.util.world

import org.bukkit.Location

fun Location.coords(): String {
    return "(x: ${this.x}, y: ${this.y}, z: ${this.z})"
}

fun Location.blockCoords(): String {
    return "(x: ${this.blockX}, y: ${this.blockY}, z: ${this.blockZ})"
}