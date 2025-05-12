package net.narzhanp.plasticarmor.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.custom.MarksmanArmorItem;
import net.narzhanp.plasticarmor.item.custom.StormtrooperArmorItem;
import net.narzhanp.plasticarmor.item.custom.TankArmorItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PlastoidArmor.MOD_ID);

    // Items
    public static final RegistryObject<Item> DEPLETED_PLASTOID_COMPOUND = ITEMS.register("depleted_plastoid_compound",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLASTOID_COMPOUND = ITEMS.register("plastoid_compound",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNFIRED_PLASTOID = ITEMS.register("unfired_plastoid",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLASTOID = ITEMS.register("plastoid",
            () -> new Item(new Item.Properties()));


    // Armor
    public static final RegistryObject<Item> STORMTROOPER_HELMET = ITEMS.register("stormtrooper_helmet",
            () -> new StormtrooperArmorItem(ModArmorMaterials.STORMTROOPER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> STORMTROOPER_CHESTPLATE = ITEMS.register("stormtrooper_chestplate",
            () -> new StormtrooperArmorItem(ModArmorMaterials.STORMTROOPER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> STORMTROOPER_LEGGINGS = ITEMS.register("stormtrooper_leggings",
            () -> new StormtrooperArmorItem(ModArmorMaterials.STORMTROOPER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> STORMTROOPER_BOOTS = ITEMS.register("stormtrooper_boots",
            () -> new StormtrooperArmorItem(ModArmorMaterials.STORMTROOPER, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> MARKSMAN_HELMET = ITEMS.register("marksman_helmet",
            () -> new MarksmanArmorItem(ModArmorMaterials.MARKSMAN, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> MARKSMAN_CHESTPLATE = ITEMS.register("marksman_chestplate",
            () -> new MarksmanArmorItem(ModArmorMaterials.MARKSMAN, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> MARKSMAN_LEGGINGS = ITEMS.register("marksman_leggings",
            () -> new MarksmanArmorItem(ModArmorMaterials.MARKSMAN, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> MARKSMAN_BOOTS = ITEMS.register("marksman_boots",
            () -> new MarksmanArmorItem(ModArmorMaterials.MARKSMAN, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> TANK_HELMET = ITEMS.register("tank_helmet",
            () -> new TankArmorItem(ModArmorMaterials.TANK, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> TANK_CHESTPLATE = ITEMS.register("tank_chestplate",
            () -> new TankArmorItem(ModArmorMaterials.TANK, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> TANK_LEGGINGS = ITEMS.register("tank_leggings",
            () -> new TankArmorItem(ModArmorMaterials.TANK, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> TANK_BOOTS = ITEMS.register("tank_boots",
            () -> new TankArmorItem(ModArmorMaterials.TANK, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        System.out.println("Registering items for plastoid_armor");
        ITEMS.register(eventBus);
    }
}
