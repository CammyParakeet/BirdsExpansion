package com.glance.birds.nest.behavior.visual

import com.glance.birds.nest.data.NestData
import com.glance.birds.vfx.AbstractVisualFeature
import org.bukkit.entity.Entity
import java.util.UUID

abstract class NestFeatureVisualizer(
    metadataPrefix: String
) : AbstractVisualFeature<NestData, UUID>(metadataPrefix) {

    override fun getId(data: NestData): UUID {
        return data.id
    }

    override fun onEntityRegistered(data: NestData, entity: Entity, key: String) {
        data.metadata += key to entity.uniqueId.toString()
    }

    override fun onRemoveVisual(data: NestData, entity: Entity, key: String) {
        super.onRemoveVisual(data, entity, key)
        data.metadata -= key
    }

}