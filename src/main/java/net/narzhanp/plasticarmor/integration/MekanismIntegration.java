package net.narzhanp.plasticarmor.integration;

import mekanism.api.MekanismAPI;
import mekanism.api.MekanismIMC;
import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.MekanismRecipe;

public class MekanismIntegration {
    public static void init() {
        // Временно закомментировано, так как recipeHelper устарел
        /*
        ItemStack input = new ItemStack(ModItems.DEPLETED_PLASTOID_COMPOUND.get());
        ItemStack output = new ItemStack(ModItems.PLASTOID_COMPOUND.get());

        MekanismAPI.recipeHelper().addEnrichingRecipe(
            ItemStackIngredient.from(input),
            output
        );
        */
    }
}