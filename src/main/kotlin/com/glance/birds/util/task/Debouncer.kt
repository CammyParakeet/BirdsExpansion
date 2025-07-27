package com.glance.birds.util.task

open class Debouncer<K>(private val cooldownMs: Long) {

    private val lastInteraction = mutableMapOf<K, Long>()

    fun canUse(key: K): Boolean {
       val now = System.currentTimeMillis()
       val last = lastInteraction[key] ?: 0L

       return if (now - last >= cooldownMs) {
           lastInteraction[key] = now
           true
       } else {
           false
       }
    }

    fun reset(key: K) {
        lastInteraction.remove(key)
    }

    fun clear() {
        lastInteraction.clear()
    }

}