package com.glance.birds.species.vanilla.goal

import com.glance.birds.nest.data.NestData
import com.glance.birds.species.BirdSpecies
import org.bukkit.entity.Mob
import org.bukkit.plugin.Plugin

class GoToNestGoal(
    private val mob: Mob,
    private val species: BirdSpecies,
    private val plugin: Plugin
) {

    private var nest: NestData? = null
    private var tickTimer = 0

    fun assignNest(nest: NestData) {
        this.nest = nest
    }

    fun tick() {
        val currentNest = nest ?: return
        // TODO: nest based retrieval to override preferred possibly?
        val behavior = species.preferredNestBehavior ?: return

        if (!behavior.shouldReturnToNest(mob, currentNest)) return

        val target = currentNest.pos.toLocation() ?: return
        val pathfinder = mob.pathfinder

        val success = pathfinder.moveTo(target)
        if (success) {
            if (tickTimer == 0) {
                behavior.onApproachNest(mob, currentNest)
            }

            behavior.onTick(mob, currentNest)
            tickTimer++

            if (tickTimer > 40 && mob.location.distanceSquared(target) < 1.0) {
                enterNest()
            }
        } else {
            tickTimer = 0
        }
    }

    private fun enterNest() {
        val currentNest = nest ?: return

        mob.setAI(false)
        mob.isSilent = true

        // todo based on data - are they allowed to enter, or be added
        //currentNest.state.occupant = ...

        species.preferredNestBehavior?.onNestEntered(mob, currentNest)

        tickTimer = 0
    }

}