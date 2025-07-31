package com.glance.birds.util.time.backoff

sealed class BackoffResult {
    data class Backoff(val delay: Long, val attempt: Int) : BackoffResult()
    data object Cooldown : BackoffResult()
}