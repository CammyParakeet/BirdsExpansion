package com.glance.birds.nest.behavior.mob.backoff

import com.glance.birds.util.time.backoff.BackoffResult
import com.glance.birds.util.time.backoff.BackoffState
import java.util.*
import kotlin.math.pow

class MobBackoffManager(
    private val baseDelayMillis: Long = 2000,       // initial time after first failure
    private val backoffMultiplier: Double = 1.5,    // growth rate
    private val maxAttempts: Int = 5,               // before full cooldown
    private val cooldownMillis: Long = 60000
) {

    private val states = mutableMapOf<UUID, BackoffState>()

    fun canAttempt(uuid: UUID): Boolean {
        val state = states[uuid] ?: return true
        val now = System.currentTimeMillis()

        if (now < state.cooldownUntil) return false
        val delay = getDelayForAttempt(state.attempts)
        return (now - state.lastAttempt) >= delay
    }

    fun registerAttempt(uuid: UUID): BackoffResult {
        val now = System.currentTimeMillis()
        val state = states.getOrPut(uuid) { BackoffState() }

        state.lastAttempt = now
        state.attempts += 1

        return if (state.attempts >= maxAttempts) {
            state.cooldownUntil = now + cooldownMillis
            BackoffResult.Cooldown
        } else {
            BackoffResult.Backoff(
                delay = getDelayForAttempt(state.attempts),
                attempt = state.attempts
            )
        }
    }

    fun reset(uuid: UUID) {
        states.remove(uuid)
    }

    private fun getDelayForAttempt(attempt: Int): Long {
        return (baseDelayMillis * backoffMultiplier.pow(attempt - 1)).toLong()
    }

}