package net.narzhanp.plasticarmor.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.narzhanp.plasticarmor.PlastoidArmor;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PlastoidArmor.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PLASTOID_ARMOR_TAB = CREATIVE_MODE_TABS.register(PlastoidArmor.MOD_ID,
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PLASTOID.get()))
                    .title(Component.translatable("creativetab.plastoid_armor_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.PLASTOID.get());
                        pOutput.accept(ModItems.DEPLETED_PLASTOID_COMPOUND.get());
                        pOutput.accept(ModItems.PLASTOID_COMPOUND.get());
                        pOutput.accept(ModItems.UNFIRED_PLASTOID.get());

                        pOutput.accept(ModItems.STORMTROOPER_HELMET.get());
                        pOutput.accept(ModItems.STORMTROOPER_CHESTPLATE.get());
                        pOutput.accept(ModItems.STORMTROOPER_LEGGINGS.get());
                        pOutput.accept(ModItems.STORMTROOPER_BOOTS.get());

                        pOutput.accept(ModItems.MARKSMAN_HELMET.get());
                        pOutput.accept(ModItems.MARKSMAN_CHESTPLATE.get());
                        pOutput.accept(ModItems.MARKSMAN_LEGGINGS.get());
                        pOutput.accept(ModItems.MARKSMAN_BOOTS.get());

                        pOutput.accept(ModItems.TANK_HELMET.get());
                        pOutput.accept(ModItems.TANK_CHESTPLATE.get());
                        pOutput.accept(ModItems.TANK_LEGGINGS.get());
                        pOutput.accept(ModItems.TANK_BOOTS.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
