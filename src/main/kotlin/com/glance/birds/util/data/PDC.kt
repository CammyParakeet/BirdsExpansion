package com.glance.birds.util.data

import com.glance.birds.BirdsExpansion
import io.papermc.paper.persistence.PersistentDataViewHolder
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataHolder
import org.bukkit.persistence.PersistentDataType
import java.util.logging.Level
import kotlin.reflect.KClass

fun PersistentDataHolder.hasPDC(key: NamespacedKey): Boolean {
    return persistentDataContainer.has(key)
}

inline fun <reified T> PersistentDataViewHolder.getPDC(key: NamespacedKey): T? {
    val type = getType(T::class) ?: run {
        BirdsExpansion
            .instance()
            .logger
            .log(Level.WARNING, "Failed to get PDC type for class '${T::class}'")
        return null
    }
    return persistentDataContainer.get(key, type) as? T
}

inline fun <reified T> PersistentDataHolder.getPDC(key: NamespacedKey): T? {
    val type = getType(T::class) ?: run {
        BirdsExpansion
            .instance()
            .logger
            .log(Level.WARNING, "Failed to get PDC type for class '${T::class}'")
        return null
    }
    return persistentDataContainer.get(key, type) as? T
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> PersistentDataHolder.setPDC(key: NamespacedKey, value: T) {
    val type = getType(T::class) as? PersistentDataType<T, T> ?: run {
        BirdsExpansion
            .instance()
            .logger
            .log(Level.WARNING, "Failed to get PDC type for '$value' as '${value::class}'")
        return
    }

    BirdsExpansion
        .instance()
        .logger
        .log(Level.WARNING, "Saving '$value' to PDC using '$type'")
    persistentDataContainer.set(key, type, value)
}

fun getType(kClass: KClass<*>): PersistentDataType<*, *>? {
    return when (kClass) {
        Int::class -> PersistentDataType.INTEGER
        Long::class -> PersistentDataType.LONG
        Double::class -> PersistentDataType.DOUBLE
        Float::class -> PersistentDataType.FLOAT
        String::class -> PersistentDataType.STRING
        Byte::class -> PersistentDataType.BYTE
        ByteArray::class -> PersistentDataType.BYTE_ARRAY
        LongArray::class -> PersistentDataType.LONG_ARRAY
        IntArray::class -> PersistentDataType.INTEGER_ARRAY
        Boolean::class -> PersistentDataType.BOOLEAN
        Short::class -> PersistentDataType.SHORT
        PersistentDataContainer::class -> PersistentDataType.TAG_CONTAINER
        else -> null
    } as? PersistentDataType<*, *> ?: return null
}
