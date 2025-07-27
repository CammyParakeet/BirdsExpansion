package com.glance.birds.vfx

interface VisualFeature<T> {
    fun updateVisual(data: T, debug: Boolean = false)
    fun updateVisual(data: T, count: Int, debug: Boolean = false) {}
    fun removeVisual(data: T, debug: Boolean = false)
}