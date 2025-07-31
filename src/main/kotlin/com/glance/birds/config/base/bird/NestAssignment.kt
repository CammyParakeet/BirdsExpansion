package com.glance.birds.config.base.bird

import org.bukkit.Particle
import org.bukkit.Sound

sealed class NestAssignment {
    data class Particles(
        val success: Particle = Particle.HAPPY_VILLAGER,
        val fail: Particle = Particle.WHITE_SMOKE,
        val cooldown: Particle = Particle.ANGRY_VILLAGER
    )

    data class Sounds(
        val success: Sound = Sound.ENTITY_VILLAGER_YES,
        val fail: Sound = Sound.BLOCK_CRAFTER_FAIL,
        val cooldown: Sound = Sound.ENTITY_VILLAGER_NO
    )
}



