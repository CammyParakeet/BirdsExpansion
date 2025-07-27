package com.glance.birds.nest.visual.handlers.base

import com.glance.birds.nest.data.NestData
import com.glance.birds.vfx.VisualFeature

class BaseFeatherVisualizer(
    private val config: BaseVisualConfig,
    private val maxVisuals: Int = 4
) : VisualFeature<NestData> {

    override fun updateVisual(data: NestData, debug: Boolean) {
        updateVisual(data, 1, debug)
    }

    override fun updateVisual(data: NestData, count: Int, debug: Boolean) {
        super.updateVisual(data, count, debug)
    }

    override fun removeVisual(data: NestData, debug: Boolean) {
        TODO("Not yet implemented")
    }
}