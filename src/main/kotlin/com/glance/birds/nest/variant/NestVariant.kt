package com.glance.birds.nest.variant

import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.data.type.NestTypeData

data class NestVariant(
    val id: String,
    val supportedTypes: Set<NestType>,
    val typeData: Map<NestType, NestTypeData>
) {
    fun getTypeData(type: NestType): NestTypeData? {
        return typeData[type]
    }
}