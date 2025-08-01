package com.glance.birds.nest.variant.recipe

import com.glance.birds.nest.variant.draft.draftNestItem
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

sealed interface NestRecipe {
    val resultKey: NamespacedKey
    fun toBukkitRecipe(): Recipe
}

data class ShapedNestRecipe(
    override val resultKey: NamespacedKey,
    val shape: List<String>,
    val ingredients: Map<Char, Material>
) : NestRecipe {
    override fun toBukkitRecipe(): Recipe {
        val recipe = ShapedRecipe(resultKey, getResultItem(""))
        recipe.shape(*shape.toTypedArray())
        ingredients.forEach { (c, m) -> recipe.setIngredient(c, m) }
        return recipe
    }
}

data class ShapelessNestRecipe(
    override val resultKey: NamespacedKey,
    val ingredients: List<Material>
) : NestRecipe {
    override fun toBukkitRecipe(): Recipe {
        val recipe = ShapelessRecipe(resultKey, getResultItem(""))
        ingredients.forEach { recipe.addIngredient(it) }
        return recipe
    }
}

fun getResultItem(variantId: String): ItemStack {
    return draftNestItem
}