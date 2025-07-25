package com.glance.birds.util.entity

import org.bukkit.entity.Display
import org.bukkit.util.Transformation

/**
 * Modifies the display entity's transformation properties through a lambda expression
 *
 * This method provides a flexible way to adjust the display's transformation
 *
 * Usage:
 * ```
 * display: ItemDisplay = ...
 * display.editTransform() {
 *    scale.set(Vector(2, 4, 2))
 *    leftRotation.set(Quaternion().rotateX(25))
 * }
 * ```
 *
 * @param action A lambda with receiver on [Transformation] to customize the display entity's transformation
 */
fun Display.editTransform(action: Transformation.() -> Unit) {
    val trans = transformation
    trans.action()
    transformation = trans
}