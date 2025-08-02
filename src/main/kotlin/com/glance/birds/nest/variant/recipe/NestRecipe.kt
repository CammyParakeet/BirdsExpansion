package com.glance.birds.nest.variant.recipe

import com.glance.birds.nest.data.type.NestType
import com.glance.birds.nest.variant.NestVariantRegistry
import com.glance.birds.util.namespacedKey
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

sealed interface NestRecipe {
    val variantId: String
    val type: NestType
    fun resultKey(): NamespacedKey
    fun toBukkitRecipe(): Recipe

    fun getResultItem(variantId: String = this.variantId, type: NestType = this.type): ItemStack {
        return NestVariantRegistry.getById(variantId)?.getTypeData(type)?.dropItem?.clone()
            ?: error("Resulting item for Nest Variant $variantId - Type ${type.name} could not be created")
    }
}

data class ShapedNestRecipe(
    override val variantId: String,
    override val type: NestType,
    val shape: List<String>,
    val ingredients: Map<Char, Material>
) : NestRecipe {
    override fun resultKey(): NamespacedKey {
        return namespacedKey("${variantId.lowercase()}_${type.name.lowercase()}_shaped")
    }

    override fun toBukkitRecipe(): Recipe {
        val recipe = ShapedRecipe(resultKey(), getResultItem())
        recipe.shape(*shape.toTypedArray())
        ingredients.forEach { (c, m) -> recipe.setIngredient(c, m) }
        return recipe
    }
}

data class ShapelessNestRecipe(
    override val variantId: String,
    override val type: NestType,
    val ingredients: List<Material>
) : NestRecipe {
    override fun resultKey(): NamespacedKey {
        return namespacedKey("${variantId.lowercase()}_${type.name.lowercase()}_shapeless")
    }

    override fun toBukkitRecipe(): Recipe {
        val recipe = ShapelessRecipe(resultKey(), getResultItem())
        ingredients.forEach { recipe.addIngredient(it) }
        return recipe
    }
}

