package com.glance.birds.util

import org.bukkit.util.EulerAngle
import org.joml.Quaternionf

fun EulerAngle.toQuaternion(): Quaternionf {
    return Quaternionf().rotateYXZ(y.toFloat(), x.toFloat(), z.toFloat())
}