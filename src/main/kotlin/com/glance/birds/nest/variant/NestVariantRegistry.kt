package com.glance.birds.nest.variant

import com.glance.birds.nest.Nest

object NestVariantRegistry {

    private val variants = mutableMapOf<String, NestVariant>()

    fun register(variant: NestVariant) {
        variants[variant.id] = variant
    }

    fun get(nest: Nest): NestVariant? {
        return getById(nest.data.variantId)
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