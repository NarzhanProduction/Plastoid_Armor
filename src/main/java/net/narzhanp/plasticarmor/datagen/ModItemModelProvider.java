package net.narzhanp.plasticarmor.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.narzhanp.plasticarmor.PlastoidArmor;
import net.narzhanp.plasticarmor.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PlastoidArmor.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.PLASTOID);

        simpleItem(ModItems.STORMTROOPER_HELMET);
        simpleItem(ModItems.STORMTROOPER_CHESTPLATE);
        simpleItem(ModItems.STORMTROOPER_LEGGINGS);
        simpleItem(ModItems.STORMTROOPER_BOOTS);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PlastoidArmor.MOD_ID, "item/" + item.getId().getPath()));
    }
}
