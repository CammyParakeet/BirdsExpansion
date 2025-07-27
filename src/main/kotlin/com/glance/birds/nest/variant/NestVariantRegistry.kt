package com.glance.birds.nest.variant

import com.glance.birds.nest.data.NestData

object NestVariantRegistry {

    private val variants = mutableMapOf<String, NestVariant>()

    fun register(variant: NestVariant) {
        variants[variant.id] = variant
    }

    fun get(nest: NestData): NestVariant? {
        return getById(nest.variantId)
    }

    fun getById(id: String): NestVariant? {
        return variants[id]
    }

    fun getAll(): Collection<NestVariant> {
        return variants.values
    }

    fun clear() {
        variants.clear()
    }

}