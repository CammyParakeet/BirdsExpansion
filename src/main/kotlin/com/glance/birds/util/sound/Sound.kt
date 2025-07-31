package com.glance.birds.util.sound

import org.bukkit.Location
import org.bukkit.Sound

fun Location.playSound(sound: net.kyori.adventure.sound.Sound) {
    world.playSound(sound)
}

fun Location.playSound(sound: Sound, vol: Float = 1.0F, pitch: Float = 1.0F) {
    world.playSound(this, sound, vol, pitch)
}