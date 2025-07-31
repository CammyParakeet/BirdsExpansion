package com.glance.birds.util.time.backoff

data class BackoffState(
    var attempts: Int = 0,
    var lastAttempt: Long = 0,
    var cooldownUntil: Long = 0
)
