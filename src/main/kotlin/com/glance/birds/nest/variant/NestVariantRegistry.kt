package com.glance.birds.nest.variant

object NestVariantRegistry {

    private val variants = mutableMapOf<String, NestVariant>()

    fun register(variant: NestVariant) {
        variants[variant.id] = variant
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