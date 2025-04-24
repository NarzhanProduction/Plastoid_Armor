package net.narzhanp.plasticarmor.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.narzhanp.plasticarmor.PlasticArmor;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PlasticArmor.MOD_ID);

    public static final RegistryObject<Item> PLASTOID = ITEMS.register("plastoid",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PLASTOID_HELMET = ITEMS.register("plastoid_helmet",
            () -> new ArmorItem(ModArmorMaterials.PLASTOID, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> PLASTOID_CHESTPLATE = ITEMS.register("plastoid_chestplate",
            () -> new ArmorItem(ModArmorMaterials.PLASTOID, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> PLASTOID_LEGGINGS = ITEMS.register("plastoid_leggings",
            () -> new ArmorItem(ModArmorMaterials.PLASTOID, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> PLASTOID_BOOTS = ITEMS.register("plastoid_boots",
            () -> new ArmorItem(ModArmorMaterials.PLASTOID, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
