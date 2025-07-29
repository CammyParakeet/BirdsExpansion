package com.glance.birds.nest.behavior.visual

import com.glance.birds.nest.Nest
import com.glance.birds.vfx.AbstractVisualFeature
import org.bukkit.entity.Entity
import java.util.UUID

abstract class NestFeatureVisualizer(
    metadataPrefix: String
) : AbstractVisualFeature<Nest, UUID>(metadataPrefix) {

    override fun getId(data: Nest): UUID {
        return data.data.id
    }

    override fun onEntityRegistered(data: Nest, entity: Entity, key: String) {
        data.data.metadata += key to entity.uniqueId.toString()
    }

    override fun onRemoveVisual(data: Nest, entity: Entity, key: String) {
        super.onRemoveVisual(data, entity, key)
        data.data.metadata -= key
    }

}