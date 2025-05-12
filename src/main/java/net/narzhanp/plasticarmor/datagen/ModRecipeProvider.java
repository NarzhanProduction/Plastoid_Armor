package net.narzhanp.plasticarmor.datagen;

import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.narzhanp.plasticarmor.PlastoidArmor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ModRecipeProvider extends net.minecraft.data.recipes.RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator.getPackOutput());
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Тестовый рецепт (mekanism:dust_iron -> mekanism:ingot_iron)
        consumer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                JsonObject input = new JsonObject();
                input.addProperty("item", "mekanism:dust_iron");
                input.addProperty("count", 1);
                json.add("input", input);

                JsonObject output = new JsonObject();
                output.addProperty("item", "mekanism:ingot_iron");
                output.addProperty("count", 1);
                json.add("output", output);
            }

            @Override
            public ResourceLocation getId() {
                return new ResourceLocation(PlastoidArmor.MOD_ID, "test_recipe");
            }

            @Override
            public RecipeSerializer<?> getType() {
                // Заглушка, так как нам не нужен точный RecipeSerializer
                return RecipeSerializer.SHAPED_RECIPE;
            }

            @Override
            public JsonObject serializeRecipe() {
                JsonObject json = new JsonObject();
                json.addProperty("type", "mekanism:enriching");
                serializeRecipeData(json);
                return json;
            }

            @Nullable
            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Nullable
            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }
        });

        // Основной рецепт (depleted_plastoid_compound -> plastoid_compound)
        consumer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                JsonObject input = new JsonObject();
                input.addProperty("item", "plastic_armor:depleted_plastoid_compound");
                input.addProperty("count", 1);
                json.add("input", input);

                JsonObject output = new JsonObject();
                output.addProperty("item", "plastic_armor:plastoid_compound");
                output.addProperty("count", 1);
                json.add("output", output);
            }

            @Override
            public @NotNull ResourceLocation getId() {
                return new ResourceLocation(PlastoidArmor.MOD_ID, "plastoid_compound");
            }

            @Override
            public @NotNull RecipeSerializer<?> getType() {
                // Заглушка
                return RecipeSerializer.SHAPED_RECIPE;
            }

            @Override
            public @NotNull JsonObject serializeRecipe() {
                JsonObject json = new JsonObject();
                json.addProperty("type", "mekanism:enriching");
                serializeRecipeData(json);
                return json;
            }

            @Nullable
            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Nullable
            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }
        });
    }
}