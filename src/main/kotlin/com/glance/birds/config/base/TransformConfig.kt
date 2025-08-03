package com.glance.birds.config.base

import com.glance.birds.util.toQuaternion
import org.bukkit.util.EulerAngle
import org.bukkit.util.Transformation
import org.bukkit.util.Vector

data class TransformConfig(
    val translation: Vector = Vector(),
    val rotBeforeScale: EulerAngle = EulerAngle(0.0, 0.0, 0.0),
    val scale: Vector = Vector(1.0, 1.0, 1.0),
    val rotAfterScale: EulerAngle = EulerAngle(0.0, 0.0, 0.0)
) {
    fun applyTo(transformation: Transformation): Transformation {
        return transformation.apply {
            this.translation.set(this@TransformConfig.translation.toVector3f())
            this.leftRotation.set(rotBeforeScale.toQuaternion())
            this.scale.set(this@TransformConfig.scale.toVector3f())
            this.rightRotation.set(rotAfterScale.toQuaternion())
        }
    }
}