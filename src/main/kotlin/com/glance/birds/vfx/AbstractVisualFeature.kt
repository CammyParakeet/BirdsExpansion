package com.glance.birds.vfx

import org.bukkit.entity.Entity
import java.util.WeakHashMap

abstract class AbstractVisualFeature<T, ID>(
    private val metadataPrefix: String
) : VisualFeature<T> {

    private val liveEntities = WeakHashMap<ID, MutableList<Entity>>()

    protected abstract fun getId(data: T): ID

    protected fun registerEntity(data: T, entity: Entity) {
        val key = (metadataPrefix + liveEntities[getId(data)]?.size)
        liveEntities.computeIfAbsent(getId(data)) { mutableListOf() }.add(entity)
        onEntityRegistered(data, entity, key)
    }

    protected open fun onEntityRegistered(data: T, entity: Entity, key: String) {}

    protected open fun onRemoveVisual(data: T, entity: Entity, key: String) {
        entity.remove()
    }

    protected fun getEntities(data: T): List<Entity> {
        return liveEntities[getId(data)] ?: emptyList()
    }

    override fun removeVisual(data: T, debug: Boolean) {
        val removed = liveEntities.remove(getId(data)) ?: return
        removed.forEachIndexed { index, entity ->
            onRemoveVisual(data, entity, "$metadataPrefix$index")
        }
    }

}