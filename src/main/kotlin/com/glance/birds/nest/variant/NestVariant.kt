package com.glance.birds.nest.variant

import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.data.type.NestTypeData
import org.bukkit.inventory.ItemStack

data class NestVariant(
    val id: String,
    val supportedTypes: Set<NestType>,
    val defaultTypeData: NestTypeData,
    val overrideTypeData: Map<NestType, NestTypeData> = emptyMap(),

    val maxOccupantSpace: Int = 4,
    val occupantEnterDistance: Double = 1.0,

    // Whether birds are visible while in the nest
    val visibleWhileResiding: Boolean = true,
) {
    fun getTypeData(type: NestType): NestTypeData {
        return overrideTypeData[type] ?: defaultTypeData
    }

    fun createNestItem(type: NestType = NestType.GROUND): ItemStack {
        return getTypeData(type).dropItem
    }

}