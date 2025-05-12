package net.narzhanp.plasticarmor.item;

import net.narzhanp.plasticarmor.PlastoidArmor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    STORMTROOPER("stormtrooper", 33, new int[]{3, 8, 6, 3}, 10,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 1f, 0.1f, () -> {
        System.out.println("Creating Ingredient for PLASTOID in " + System.currentTimeMillis());
        return Ingredient.of(ModItems.PLASTOID.get());
    }),
    MARKSMAN("marksman", 42, new int[]{3, 8, 6, 3}, 13,
    SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0.2f, () -> {
        System.out.println("Creating Ingredient for MARKSMAN in " + System.currentTimeMillis());
        return Ingredient.of(ModItems.PLASTOID.get());
    }),
    TANK("tank", 75, new int[]{6, 12, 8, 5}, 16,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 3f, 0.3f, () -> {
        System.out.println("Creating Ingredient for TANK in " + System.currentTimeMillis());
        return Ingredient.of(ModItems.PLASTOID.get());
    });

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = {11, 16, 16, 13};

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound,
                      float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public @NotNull String getName() {
        return PlastoidArmor.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}