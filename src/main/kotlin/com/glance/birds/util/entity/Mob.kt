package com.glance.birds.util.entity

import com.glance.birds.util.data.getPDC
import com.glance.birds.util.data.removePDC
import com.glance.birds.util.data.setPDC
import com.glance.birds.util.namespacedKey
import org.bukkit.entity.Mob
import java.util.UUID

val MOB_ASSIGNED_NEST_KEY = namespacedKey("assigned_nest")

fun Mob.getAssignedNestId(): String? =
    getPDC<String>(MOB_ASSIGNED_NEST_KEY)

fun Mob.hasAssignedNest(): Boolean = getAssignedNestId() != null

fun Mob.setAssignedNestId(nestId: UUID) {
    setPDC(MOB_ASSIGNED_NEST_KEY, nestId.toString())
}

fun Mob.clearAssignedNest() {
    removePDC(MOB_ASSIGNED_NEST_KEY)
}