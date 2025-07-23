package com.glance.birds.nest.variant

import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.data.type.NestTypeData

data class NestVariant(
    val id: String,
    val supportedTypes: Set<NestType>,
    val defaultTypeData: NestTypeData,
    val overrideTypeData: Map<NestType, NestTypeData> = emptyMap()
) {
    fun getTypeData(type: NestType): NestTypeData {
        return overrideTypeData[type] ?: defaultTypeData
    }
}