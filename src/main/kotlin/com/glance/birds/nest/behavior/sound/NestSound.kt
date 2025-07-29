package com.glance.birds.nest.behavior.sound

import com.glance.birds.nest.Nest
import org.bukkit.Location
import org.bukkit.Sound

fun Location.playSound(sound: net.kyori.adventure.sound.Sound) {
    world.playSound(sound)
}

fun Location.playSound(sound: Sound, vol: Float = 1.0F, pitch: Float = 1.0F) {
    world.playSound(this, sound, vol, pitch)
}

// TODO a sound config retriever

fun Nest.playBreakSound() {
    location?.apply {
        playSound(Sound.BLOCK_MOSS_CARPET_BREAK)
        playSound(Sound.BLOCK_GRASS_BREAK)
        playSound(Sound.BLOCK_CHERRY_SAPLING_BREAK)

        if (this@playBreakSound.state.hasEggs) {
            playSound(Sound.ENTITY_TURTLE_EGG_CRACK)
        }
        // feathers?
    }
}

fun Nest.playPlaceSound() {
    location?.apply {
        playSound(Sound.BLOCK_MOSS_CARPET_PLACE)
        playSound(Sound.BLOCK_GRASS_PLACE)
        playSound(Sound.BLOCK_CHERRY_SAPLING_PLACE)
    }
}

fun Nest.playAddEggSound() {
    location?.apply {
        playSound(Sound.ENTITY_TURTLE_EGG_CRACK)
        playSound(Sound.ENTITY_TURTLE_LAY_EGG)
    }
}

fun Nest.playAddFeatherSound() {
    location?.apply {
        playSound(Sound.BLOCK_WOOL_PLACE)
        playSound(Sound.BLOCK_MOSS_CARPET_PLACE)
    }
}

fun Nest.playExtractEggSound() {
    location?.apply {
        playSound(Sound.ENTITY_TURTLE_EGG_CRACK)
        playSound(Sound.ENTITY_CHICKEN_EGG)
    }
}

fun Nest.playExtractFeatherSound() {
    location?.apply {
        playSound(Sound.BLOCK_MOSS_CARPET_BREAK)
        playSound(Sound.ENTITY_ITEM_PICKUP)
    }
}

fun Nest.playExtractTrinketSound() {
    location?.apply {
        playSound(Sound.BLOCK_AMETHYST_BLOCK_PLACE)
        playSound(Sound.ENTITY_ITEM_PICKUP)
    }
}

