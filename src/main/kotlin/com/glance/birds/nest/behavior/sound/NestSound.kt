package com.glance.birds.nest.behavior.sound

import com.glance.birds.nest.data.NestData
import org.bukkit.Location
import org.bukkit.Sound

fun Location.playSound(sound: net.kyori.adventure.sound.Sound) {
    world.playSound(sound)
}

fun Location.playSound(sound: Sound, vol: Float = 1.0F, pitch: Float = 1.0F) {
    world.playSound(this, sound, vol, pitch)
}

// TODO a sound config retriever

fun NestData.playBreakSound() {
    pos.toLocation()?.apply {
        playSound(Sound.BLOCK_MOSS_CARPET_BREAK)
        playSound(Sound.BLOCK_GRASS_BREAK)
        playSound(Sound.BLOCK_CHERRY_SAPLING_BREAK)

        if (this@playBreakSound.state.hasEggs) {
            playSound(Sound.ENTITY_TURTLE_EGG_CRACK)
        }
        // feathers?
    }
}

fun NestData.playPlaceSound() {
    pos.toLocation()?.apply {
        playSound(Sound.BLOCK_MOSS_CARPET_PLACE)
        playSound(Sound.BLOCK_GRASS_PLACE)
        playSound(Sound.BLOCK_CHERRY_SAPLING_PLACE)
    }
}

fun NestData.playAddEggSound() {
    pos.toLocation()?.apply {
        playSound(Sound.ENTITY_TURTLE_EGG_CRACK)
        playSound(Sound.ENTITY_TURTLE_LAY_EGG)
    }
}

fun NestData.playAddFeatherSound() {
    pos.toLocation()?.apply {
        playSound(Sound.BLOCK_WOOL_PLACE)
        playSound(Sound.BLOCK_MOSS_CARPET_PLACE)
    }
}

fun NestData.playExtractEggSound() {
    pos.toLocation()?.apply {
        playSound(Sound.ENTITY_TURTLE_EGG_CRACK)
        playSound(Sound.ENTITY_CHICKEN_EGG)
    }
}

fun NestData.playExtractFeatherSound() {
    pos.toLocation()?.apply {
        playSound(Sound.BLOCK_MOSS_CARPET_BREAK)
        playSound(Sound.ENTITY_ITEM_PICKUP)
    }
}

fun NestData.playExtractTrinketSound() {
    pos.toLocation()?.apply {
        playSound(Sound.BLOCK_AMETHYST_BLOCK_PLACE)
        playSound(Sound.ENTITY_ITEM_PICKUP)
    }
}

